package mysite.expense.service;

import lombok.RequiredArgsConstructor;
import mysite.expense.dto.UserDTO;
import mysite.expense.entity.User;
import mysite.expense.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository uRepo;
    private final ModelMapper modelMapper;  //DTO <--> entity변환

    public void save(UserDTO userDTO) {
        User user = mapToUser(userDTO);
        user.setUserId(UUID.randomUUID().toString());   //중복되지 않는 id값 생성
        uRepo.save(user);
    }
    
    public User mapToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
