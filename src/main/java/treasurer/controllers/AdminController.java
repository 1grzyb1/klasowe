package treasurer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import treasurer.entity.Contribution;
import treasurer.entity.History;
import treasurer.entity.People;
import treasurer.repository.ContributionRepository;
import treasurer.repository.HistoryRepository;
import treasurer.repository.UserRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController extends HttpServlet {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private ContributionRepository contributionRepository;

    @PostMapping(path = "/addUser")
    public @ResponseBody
    String addNewUser(@RequestParam String name
            , @RequestParam int money, @RequestParam int nobility) {

        People n = new People();
        n.setName(name);
        n.setMoney(money);
        n.setNobility(nobility);
        System.out.println(n);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping("/getAll")
    public @ResponseBody
    Iterable<People> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/getHistory")
    public @ResponseBody
    Iterable<History> getHistory(){
        return historyRepository.findAll();
    }


    @GetMapping("/getCurrent")
    public @ResponseBody
    float getCurrent(){
        float money = 0;
        for (History i: historyRepository.findAll()){
            money = money + i.getMoney();
        }
        return money;
    }

    @Transactional
    @PostMapping("/addPayment")
    public @ResponseBody
    String addPayment(@RequestParam float money, @RequestParam String what){
        History history = new History();
        history.setMoney(money);
        history.setWhat(what);
        historyRepository.save(history);
        return "payment";
    }

    @PostMapping("/addContribution")
    public @ResponseBody
    void addContribution(@RequestParam String name){
        for (People p: userRepository.findAll()){
            Contribution contribution = new Contribution();
            contribution.setChecked(0);
            contribution.setContribution(name);
            contribution.setName(p.getName());
            contributionRepository.save(contribution);
        }
    }

    @PostMapping("/deleteContribution")
    public @ResponseBody
    String deleteContribution(@RequestParam String name){
        for (Contribution p: contributionRepository.findAll()){
            if (p.getContribution().equals(name)){
                contributionRepository.delete(p);
            }
        }
        return "added";
    }

    @GetMapping("/getSkladki")
    public @ResponseBody
    Iterable<String> getSkladki(){
        return contributionRepository.findDistinctContributionByContribution();
    }

    @GetMapping("/getContribution")
    public @ResponseBody
    Iterable<Contribution> getContribution(@Param("contribution") String contribution){
        return contributionRepository.findByContribution(contribution);
    }

    @Transactional
    @PostMapping("/updateContribution")
    public @ResponseBody
    String updateContribution(@Param("name") String name, @Param("checked") int checked, @Param("contribution") String contribution){
        for(People p: userRepository.findByName(name)){
            contributionRepository.updateContribution(name, checked, contribution);
        }
        return "ok";
    }
}

