package com.example.dao.impl;

import com.example.domain.User;
import com.example.utils.XmlUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDaoImpl implements com.example.dao.UserDao {
    @Override
    public void add(User user) {
        try {
            Document doc = XmlUtils.getDocument();
            Element root = doc.getRootElement();
            Element userElem = root.addElement("user");

            userElem.addElement("id").setText(user.getId());
            userElem.addElement("username").setText(user.getUsername());
            userElem.addElement("password").setText(user.getPassword());
            userElem.addElement("email").setText(user.getEmail());

            Element birthdayElem = userElem.addElement("birthday");
            Date birthday = user.getBirthday();
            if (birthday != null) {
                String format = new SimpleDateFormat("yyyy-MM-dd").format(birthday);
                birthdayElem.setText(format);
            }

            userElem.addElement("nickname").setText(user.getNickname());

            XmlUtils.writeToXml(doc);
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User find(String username, String password) {
        try {
            Document doc = XmlUtils.getDocument();
            Node userNode = doc.selectSingleNode("//user[username='" + username + "' and password='" + password + "']");

            if (userNode == null) {
                return null;
            }

            User user = new User();
            String id = userNode.valueOf("./id");
            String email = userNode.valueOf("./email");
            String nickname = userNode.valueOf("nickname");
            String birthday = userNode.valueOf("birthday");

            user.setUsername(username);
            user.setPassword(password);
            user.setId(id);
            user.setEmail(email);
            user.setNickname(nickname);

            if (birthday == null || birthday.trim().length() == 0) {
                user.setBirthday(null);
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.parse(birthday);
            }

            return user;
        } catch (DocumentException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(String username) {
        try {
            Document doc = XmlUtils.getDocument();
            Node userNode = doc.selectSingleNode("//user[username='" + username + "']");
            return userNode != null;
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}
