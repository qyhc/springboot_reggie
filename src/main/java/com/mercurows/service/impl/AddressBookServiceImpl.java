package com.mercurows.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mercurows.domain.AddressBook;
import com.mercurows.mapper.AddressBookMapper;
import com.mercurows.service.AddressBookService;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper,AddressBook> implements AddressBookService{
}
