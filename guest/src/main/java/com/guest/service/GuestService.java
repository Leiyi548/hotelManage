package com.guest.service;

import com.guest.pojo.po.Guest;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
@Service
public interface GuestService extends IService<Guest> {

    List<Guest> getByIdCard(String idCard);

    List<Guest> getByContact(String contact);

    List<Guest> getByName(String name);
}
