package com.tagihan.Tagihan.Rabbit.User.Consumer;

import com.tagihan.Tagihan.Model.User;
import com.tagihan.Tagihan.Rabbit.User.Producer.UserProducerRepository;
import com.tagihan.Tagihan.Repository.User.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserConsumer {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    UserProducerRepository userProducerRepository;

    @RabbitListener(queues = "q.input.registrasi")
    public void addRegistrasi(User user){
        user.setId(0);
        String userBpjs = user.getNoBpjs();
        String userPln = user.getNoPln();
        String userIndihome = user.getNoIndihome();
        List<User> userList = userRepository.findAllUser();
        for (int i = 0; i< userList.size(); i++){
            User user1 = userList.get(i);
            if (user.getNoBpjs().equals(userBpjs) || user.getNoPln().equals(userPln) || user.getNoIndihome().equals(userIndihome)){
                userRepository.addUser(null);
                break;
            }
        }
        userRepository.addUser(user);

        rabbitTemplate.convertSendAndReceive("xregistrasi.controller","",user);
    }

    User userKeRabbit = new User();
    @RabbitListener(queues = "q.get.registrasi")
    public void getUsername(User user){
        User user1 = userRepository.findByUserName(user.getUserName());
        userKeRabbit = user1;
//        userProducerRepository.getUsername(userKeRabbit);
        rabbitTemplate.convertSendAndReceive("xusernameregis.controller","",userKeRabbit);
    }

    @RabbitListener(queues = "q.hapus.registrasi")
    public void hapusUser(int id){
        userRepository.deleteUser(id);
    }

    @RabbitListener(queues = "q.edit.registrasi")
    public void editUser (User user){
        userRepository.editUser(user);
        rabbitTemplate.convertSendAndReceive("xeditregis.controller","",user);
    }
}
