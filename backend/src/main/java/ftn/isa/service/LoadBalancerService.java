package ftn.isa.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class LoadBalancerService {

    @Value("${raft.server.urls}")
    private String[] raftServerUrls;

    public String distributeRequest() {
        // Logika potrebna za distribuciju servisa Raft servera koristeci Nginx
        // U okviru ovoga se moze koristiti load balancing algoritam
        String selectedServer = selectServer();

        logSelectedServer(selectedServer);
        return selectedServer;
    }

    private String selectServer() {
        int randomIndex = (int) (Math.random() * raftServerUrls.length);
        return raftServerUrls[randomIndex];
    }

    private void logSelectedServer(String selectedServer) {
        System.out.println("Selected Raft Server: " + selectedServer);
    }
}
