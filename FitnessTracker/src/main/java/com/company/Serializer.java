package com.company;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.company.PathToFile.pathToFile;


public class Serializer {
    private JAXBContext context;
    private String pathToUsersFile;

//    public Serializer(String pathToUsersFile) throws JAXBException {
//        this.pathToUsersFile = pathToUsersFile;
//        context = JAXBContext.newInstance(Users.class);
//    }

    public Serializer() throws JAXBException {
        this.pathToUsersFile = pathToFile;
        context = JAXBContext.newInstance(Users.class);
    }

    public void serialize(Users users) throws JAXBException {
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        try {
            marshaller.marshal(users, new FileOutputStream(pathToUsersFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<User> deserialize() throws JAXBException {
        File xml = new File(pathToUsersFile);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        List<User> users = ((Users) unmarshaller.unmarshal(xml)).getUsers();
        return users;
    }

    public void setContext(JAXBContext context) {
        this.context = context;
    }
    public void setPathToUsersFile(String pathToUsersFile) {
        this.pathToUsersFile = pathToUsersFile;
    }
}
