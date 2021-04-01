package com.company;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class UserManaging {

    static User newUser;
    static Users newUsersList = new Users();
    static Serializer serializer;

    public static boolean registration(String login) {
        try {
            newUser = new User();
            newUser.setLogin(login);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        try {
            serializer = new Serializer();
            List<User> users = serializer.deserialize();
            boolean isUnique = false;
            if (users != null) {
                isUnique = users.stream().anyMatch(i -> i.getLogin().equals(login));
            }
            else users = new ArrayList<>();
            if (!isUnique) {                                        //проверка на существующего юзера!!!!!!
                newUsersList.setUsers(Arrays.asList(newUser));
                users.add(newUser);
                newUsersList.setUsers(users);
                serializer.serialize(newUsersList);
                return true;
            }
            else return false;
        } catch (JAXBException | NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<User> loginUser(String login) throws JAXBException {
        List<User> usersList = serializer.deserialize();
        Optional<User> activeUser = usersList.stream().filter(i -> i.getLogin().equals(login)).findFirst();
        if (activeUser.isPresent()) {
            return activeUser;
        } else {
            // registration(login);
        }
        return null;
    }
    public static void updateUserInfo(User user){
        try {
            serializer = new Serializer();
            List<User> users = serializer.deserialize();
            users.stream().filter(i -> i.getLogin().equals(user.getLogin())).forEach(i -> {
                i.setLastCalories(user.getLastCalories());
                i.setTotalCalories(i.getTotalCalories() + user.getLastCalories());
            });
            Users updatedUser = new Users();
            updatedUser.setUsers(users);
            serializer.serialize(updatedUser);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
