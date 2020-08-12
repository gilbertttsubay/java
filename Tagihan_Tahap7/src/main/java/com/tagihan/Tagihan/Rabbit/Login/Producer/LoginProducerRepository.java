package com.tagihan.Tagihan.Rabbit.Login.Producer;

import com.tagihan.Tagihan.Model.Login;

public interface LoginProducerRepository {
    public void addLogin(Login login);
    public void logout(Login login);
}
