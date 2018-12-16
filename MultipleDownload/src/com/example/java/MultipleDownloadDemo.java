package com.example.java;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// 多线程下载
public class MultipleDownloadDemo {
    private static String path = "http://127.0.0.1:8080/mytest/GooglePinyinInstaller.exe";
    private static final String fileName = "test/" + path.substring(path.lastIndexOf("/") + 1);

    // 同时下载的线程数
    private static final int DOWNLOAD_THREADS = 3;

    // 还未完成下载的线程数。当此变量值为0时说明全部线程下载完毕，此时需要删除保存每个线程下载位置的文件
    private static int downloading_threads = DOWNLOAD_THREADS;

    public static void main(String[] args) {
        try {
            // 连接服务端，获取待下载文件的大小
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int respCode = conn.getResponseCode();
            if (respCode == 200) {
                int contentLength = conn.getContentLength();
                System.out.println("待下载文件大小: " + contentLength + "字节");

                // 预分配空间。客户端新建一个大小和待下载文件大小一样的文件。
                RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
                raf.setLength(contentLength);
                raf.close();

                // 计算每个下载线程的起止位置
                int blockSize = contentLength / DOWNLOAD_THREADS;
                for (int i = 0; i < DOWNLOAD_THREADS; i++) {
                    int startIndex = i * blockSize;
                    int endIndex = startIndex + blockSize - 1;

                    // 最后一个线程除了需要下载平均分配的大小外，还要加上多出来的部分（contentLength / DOWNLOAD_THREADS的余数）
                    if (i == DOWNLOAD_THREADS - 1) {
                        endIndex = contentLength - 1; // 直到文件末尾
                    }
                    System.out.printf("线程%s下载文件的理论起止位置: %d-%d%n", i, startIndex, endIndex);

                    // 启动线程开始下载
                    new DownloadThread(startIndex, endIndex, i).start();
                }
            } else {
                System.err.println("服务端返回的状态码不是预期的200，而是" + respCode);
            }
        } catch (MalformedURLException e) {
            System.err.println("URL格式错误");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class DownloadThread extends Thread {
        private int startIndex;
        private int endIndex;
        private int id;

        DownloadThread(int startIndex, int endIndex, int id) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.id = id;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            InputStream in = null;
            try {
                URL url = new URL(path);
                conn = (HttpURLConnection) url.openConnection();

                // 支持断点续传功能。查询是否有上次保存的下载位置，有的话说明上次下载没有完成，可以从上次的位置开始继续下载，而不是从头开始下载
                File lastPositionFile = new File(String.format("%s-%d.txt", fileName, id));
                int downloaded = 0; // 已下载的大小
                int total = endIndex - startIndex + 1; // 总共需要下载的大小。注意位置从0开始，大小要在位置的基础上+1
                if (lastPositionFile.exists() && lastPositionFile.length() > 0) {
                    BufferedReader reader = new BufferedReader(new FileReader(lastPositionFile));
                    int lastPosition = Integer.parseInt(reader.readLine());
                    downloaded = lastPosition - startIndex + 1;
                    if (lastPosition <= endIndex) {
                        startIndex = lastPosition + 1;
                    } else {
                        System.err.println("错误！查询出来的lastPosition大于endIndex");
                        return;
                    }
                    reader.close();
                }
                System.out.printf("线程%s下载文件的真实起止位置: %d-%d%n", id, startIndex, endIndex);
                if (startIndex > endIndex) {
                    System.err.println("出错了。startIndex > endIndex");
                    return;
                }

                // 重要！ 设置下载文件的起止位置。这一步绝对不能忘，否则就是下载全部而不是部分了。
                conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
                int respCode = conn.getResponseCode();

                // HTTP状态码206表示请求部分内容成功，注意不是200
                if (respCode == 206) {
                    in = conn.getInputStream();
                    RandomAccessFile raf = new RandomAccessFile(fileName, "rwd");
                    raf.seek(startIndex);
                    byte[] buf = new byte[1024 * 1024]; // 1M-5M比较合适。太小下载慢，太大如果失败，这部分得重下
                    int len;
                    int count = 0; // 本次已下载的字节数
                    while ((len = in.read(buf)) != -1) {
                        raf.write(buf, 0, len);
                        count += len;

                        // 保存每次下载到哪的位置，以支持断点续传功能。这样即使下载中断（比如由于突然断网等），下次只需继续下载未完成的部分而不用重新下载

                        // 不能使用FileOutputStream，因为该类会先把数据存到缓存，再写入文件，如果刚好在这之间断掉，就保存失败。
                        // 使用RandomAccessFile，设置模式为rwd，可以直接写入文件
                        RandomAccessFile raf2 = new RandomAccessFile(lastPositionFile, "rwd");
                        int position = startIndex + count;
                        raf2.write(String.valueOf(position).getBytes());
                        raf2.close();
                        downloaded += len;
                        System.out.printf("线程%s完成 %6.2f%% (= %d / %d)%n", id, downloaded * 100.0 / total, downloaded, total);
                    }
                    raf.close();
                    System.out.println("线程" + id + "下载完毕");

                    // 检测是否所有线程下载完毕，全部下载完成需要删除保存下载位置的文件
                    deletePositionFiles();
                } else {
                    System.err.println("服务器返回的状态码不是206.而是" + respCode);
                }
            } catch (MalformedURLException e) {
                System.err.println("URL格式错误");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }

        private void download(InputStream in, File lastPositionFile) throws IOException {

        }

        private void deletePositionFiles() {
            synchronized (DownloadThread.class) {
                downloading_threads--;
                // 所有线程下载完毕，删除保存下载位置的文件
                if (downloading_threads == 0) {
                    System.out.println("下载完成！");

                    // 当前下载线程怎么知道其他下载线程保存位置的文件名称呢？
                    // 只要这些文件名取的有规律就行
                    // 比如为fileName + id，而id为0，1，2数字
                    for (int i = 0; i < DOWNLOAD_THREADS; i++) {
                        File file = new File(String.format("%s-%d.txt", fileName, i));
                        if (!file.delete()) {
                            file.deleteOnExit(); // 立即删除失败，那就在JVM关闭前再删除一次
                        }
                    }
                }
            }
        }
    }
}

