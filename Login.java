import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Login {

    private String userFile;
    private String userID;

    public Login(String userFile) {
        this.userFile = userFile;
    }

    private List<User> loadUsers(){
        List<User> users = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(userFile))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                if (parts.length == 4){
                    users.add(new User(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3])));
                } else {
                    System.out.println("Invalid user format: " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading user file: " + e.getMessage());
        }
        return users;
    }

    private void saveUsers(List<User> users) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(userFile))) {
            for(User user : users){
                bw.write(user.getUserID()+ "," + user.getUsername() + "," + user.getPassword() + "," + user.isAdmin() + "\n");
            }
        } catch(IOException e) {
            System.out.println("Error writing user file: " + e.getMessage());

        }
    }

    public boolean register(String userName, String password) {
        List<User> users = loadUsers();
        if (users.stream().anyMatch(user -> user.getUsername().equals(userName)))
        {
            System.out.println("Username already exists");
            return false;
        }

        users.add(new User(userID, userName, password, false));
        saveUsers(users);
        return true;
    }

    public User login(String userName, String password) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(userName) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
