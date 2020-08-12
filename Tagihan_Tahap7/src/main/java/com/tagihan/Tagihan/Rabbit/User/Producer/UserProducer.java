package com.tagihan.Tagihan.Rabbit.User.Producer;

import com.tagihan.Tagihan.Model.User;
import com.tagihan.Tagihan.Repository.User.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserProducer implements UserProducerRepository {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    UserRepository userRepository;


    public void addRegistrasi(User user){
        rabbitTemplate.setReplyTimeout(20000);
        rabbitTemplate.convertSendAndReceive("x.input.registrasi","",user);
    }

    public void getUsername(User user){
        rabbitTemplate.setReplyTimeout(40000);
        rabbitTemplate.convertSendAndReceive("x.get.registrasi","",user);

    }

    public void getList(){
        rabbitTemplate.setReplyTimeout(20000);
        List<User> userList1 = userRepository.findAllUser();
            rabbitTemplate.convertSendAndReceive("x.getlist.registrasi","",userList1);


    }

    public void hapusUser(int idUser){
        rabbitTemplate.setReplyTimeout(20000);
        String jsonId = Integer.toString(idUser);
        rabbitTemplate.convertSendAndReceive("x.hapus.registrasi","",jsonId);
    }

    public void editUser(User user){
        rabbitTemplate.setReplyTimeout(20000);
        rabbitTemplate.convertSendAndReceive("x.edit.registrasi","",user);
    }

}
