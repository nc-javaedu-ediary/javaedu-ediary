package com.ncjavaedu.ediary.dao.impl;

import com.ncjavaedu.ediary.dao.UserDao;
import com.ncjavaedu.ediary.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDaoImpl extends BaseDao<Integer, User> implements UserDao {
    @Transactional
    public List<User> list()  {
        Criteria criteria = createEntityCriteria();
        return (List<User>) criteria.list();
    }

    @Transactional
    public User getUser(String login, String password) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.like("login", login));
        criteria.add(Restrictions.like("password", password));
        return (User) criteria.uniqueResult();
    }

    @Transactional
    public void save(User user) {
        getSession().saveOrUpdate(user);
    }
}
