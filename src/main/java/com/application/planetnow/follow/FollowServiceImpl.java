package com.application.planetnow.follow;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {


    @Autowired
    private FollowDAO  followDAO;

}
