package app.server;

// TODO 1: IMPORT USED MODELS AND REPOSITORIES
import app.services.IObserver;
import app.services.IServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServicesImpl implements IServices {

    // TODO 2: DEFINE/ADD REPOSITORIES

    private static final Logger logger = LogManager.getLogger(ServicesImpl.class);

    private Map<String, IObserver> loggedClients;

    // TODO 3: ADD REPOSITORIES TO CONSTRUCTOR
    public ServicesImpl() {
        logger.info("Initializing ServicesImpl");
        loggedClients = new ConcurrentHashMap<>();
    }

    // TODO 4: DON'T FORGET TO CALL THE OBSERVERS FROM CONTROLLERS/CLIENTS WHEN NEEDED (IN THE SPECIFIC METHODS THAT NEED TO NOTIFY THE CLIENTS)

//    public synchronized User login(User user, IObserver client) throws AppException {
//        User foundUser = searchUserByName(user.getName());
//        if (foundUser != null){
//            if(loggedClients.get(user.getName()) != null)
//                throw new AppException("User already logged in.");
//            if (user.getPassword().equals(foundUser.getPassword())) {
//                loggedClients.put(user.getName(), client);
//            } else
//                throw new AppException("Authentication failed.");
//        } else
//            throw new AppException("Authentication failed.");
//        return user;
//    }
//
//    public synchronized User searchUserByName(String name) throws AppException {
//        Iterable<User> users = userRepository.findAll();
//        for (User user : users) {
//            if (user.getName().equals(name)) {
//                return user;
//            }
//        }
//        return null;
//    }
//
//    public synchronized User logout(User user, IObserver client) throws AppException {
//        IObserver localClient = loggedClients.remove(user.getName());
//        if (localClient == null)
//            throw new AppException("User " + user.getName() + " is not logged in.");
//        return user;
//    }
}
