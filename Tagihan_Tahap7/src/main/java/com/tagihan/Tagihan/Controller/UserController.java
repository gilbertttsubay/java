package com.tagihan.Tagihan.Controller;

import com.tagihan.Tagihan.Model.User;
import com.tagihan.Tagihan.Rabbit.User.Producer.UserProducerRepository;
import com.tagihan.Tagihan.Repository.User.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProducerRepository userProducerRepository;

    User userDariRabbit = new User();

    @RabbitListener(queues = "qregistrasi.controller")
    public void getRegistrasi(User user){
        userDariRabbit = user;
    }
    @PostMapping("registrasi")
    public User registrasiUser(@RequestBody User user){
//        user.setId(0);
//
//        userRepository.addUser(user);

        userProducerRepository.addRegistrasi(user);
        return user;
    }

    User userGetUsername = new User();
    @RabbitListener(queues = "q.get.registrasi")
    public void getUsernameDariRabbit(User user){
        userGetUsername = user;
    }
    @GetMapping("user")
    public User getUsername(@RequestBody User user){
//        User user1 = userRepository.findByUserName(user.getUserName());
        userProducerRepository.getUsername(user);
        if (userGetUsername == null){
            return null;
        }
        return userGetUsername;
    }

    List<User> userListDariRabbit = null;

    @RabbitListener(queues = "q.getlist.registrasi")
    public void getUserListDariRabbit(List<User> userList){
        userListDariRabbit = userList;
    }

    @GetMapping("semuauser")
    public List<User> getSemuaList(){
        userProducerRepository.getList();
        System.out.println("Kirim permintaan list");
        return userListDariRabbit;
    }

    @DeleteMapping("hapusUser/{idUser}")
    public String hapusUser(@PathVariable int idUser){
        userProducerRepository.hapusUser(idUser);
//        userRepository.deleteUser(idUser);
        return "sudah dihapus";
    }
User userEditDariRabbit = new User();
    @RabbitListener(queues = "qeditregis.controller")
    public void getEditDariRabbit(User user){
        userEditDariRabbit = user;
    }

    @PutMapping("edituser")
    public User editUser(@RequestBody User user){

        userProducerRepository.editUser(user);
//        userRepository.editUser(user);
        return userEditDariRabbit   ;
    }
}
