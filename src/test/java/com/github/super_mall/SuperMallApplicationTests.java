package com.github.super_mall;

import com.github.super_mall.controller.itemController.ItemController;
import com.github.super_mall.dto.itemDto.ItemAdditionalDto;
import com.github.super_mall.service.itemService.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@SpringBootTest
class SuperMallApplicationTests {

    @Test
    void contextLoads() {
    }


}
