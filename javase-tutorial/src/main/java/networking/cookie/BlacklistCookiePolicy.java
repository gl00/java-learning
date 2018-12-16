package networking.cookie;

import java.net.*;

public class BlacklistCookiePolicy implements CookiePolicy {
    private String[] blacklist;

    public BlacklistCookiePolicy(String[] blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public boolean shouldAccept(URI uri, HttpCookie cookie) {
        if (uri == null || cookie == null) {
            return false;
        }

        String host;
//        try {
//            host = InetAddress.getByName(uri.getHost()).getCanonicalHostName();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
            host = uri.getHost();
//        }

        for (String aBlacklist : blacklist) {
            if (HttpCookie.domainMatches(aBlacklist, host)) {
                return false;
            }
        }

        return CookiePolicy.ACCEPT_ORIGINAL_SERVER.shouldAccept(uri, cookie);
    }
}
