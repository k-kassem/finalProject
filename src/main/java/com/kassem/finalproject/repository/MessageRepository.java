package com.kassem.finalproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.kassem.finalproject.model.Message;

public interface MessageRepository extends CrudRepository<Message, Long>{

}
