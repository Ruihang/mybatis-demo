package top.ruix.dao;

import top.ruix.entity.UserEntity;

public interface IUserDao {

    UserEntity findById(int id);
}
