package start;

import transport.model.ReservationManager;
import transport.services.rest.ServiceException;
import org.springframework.web.client.RestClientException;
import rest.client.TripsClient;


public class StartRestTemplateClient {
    private final static TripsClient usersClient=new TripsClient();
    public static void main(String[] args) {
        //  RestTemplate restTemplate=new RestTemplate();
        ReservationManager userT = new ReservationManager("test2024","133");
        try{
            //  User result= restTemplate.postForObject("http://localhost:8080/chat/users",userT, User.class);

            //  System.out.println("Result received "+result);
      /*  System.out.println("Updating  user ..."+userT);
        userT.setName("New name 2");
        restTemplate.put("http://localhost:8080/chat/users/test124", userT);

*/
            // System.out.println(restTemplate.postForObject("http://localhost:8080/chat/users",userT, User.class));
            //System.out.println( restTemplate.postForObject("http://localhost:8080/chat/users",userT, User.class));
            System.out.println("Adding a new user "+userT);
            show(()-> System.out.println(usersClient.create(userT)));
            System.out.println("\n  Printing all users ...");
            show(()->{
                ReservationManager[] res=usersClient.getAll();
                for(ReservationManager u:res){
                    System.out.println(u.getId()+": "+u.getName());
                }
            });
        } catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }

        System.out.println("\n  Info for user with id=ana");
        show(()-> System.out.println(usersClient.getById("ana")));
    }



    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            //  LOG.error("Service exception", e);
            System.out.println("Service exception"+ e);
        }
    }
}
