package mapper;

import entity.User;

/**
 * @Author: huangYong
 * @Date: 2020/5/8 18:01
 */
public interface UserMapper {
    User selectByPrimary(Long id);
}
