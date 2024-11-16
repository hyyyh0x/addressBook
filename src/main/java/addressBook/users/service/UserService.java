package addressBook.users.service;

import addressBook.users.dto.UserDTO;
import addressBook.users.entity.Users;
import addressBook.users.repository.UsersRepository;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository userRepository;

    public List<UserDTO> getAllUsers(String search) {
        List<Users> users;
        if (StringUtils.hasText(search)) {
            users = userRepository.findAllByNameContaining(search);
        } else {
            users = userRepository.findAll();
        }
        return users.stream()
            .sorted(Comparator.comparing(Users::getName))
            .map(user -> new UserDTO(
                user.getId(),
                user.getName(),
                user.getPhone(),
                user.getPrayerNote(),
                user.getPicture()))
            .toList();
    }

    public UserDTO getUserById(Long userId) {
        Users user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("없는 아이디입니다."));
        return new UserDTO(user.getId(), user.getName(), user.getPhone(), user.getPrayerNote(),
            user.getPicture());
    }

    public UserDTO createUser(UserDTO userDTO) {
        if(userRepository.existsByPhoneAndName(userDTO.phone(), userDTO.name())){
            throw new IllegalArgumentException("존재하는 사람입니다.");
        }
        Users users = new Users(userDTO.name(), userDTO.phone(), userDTO.prayerNote(),
            userDTO.picture());
        users = userRepository.save(users);
        return new UserDTO(users.getId(), users.getName(), users.getPhone(), users.getPrayerNote(),
            users.getPicture());
    }

    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        if(userRepository.existsByPhoneAndNameAndIdNot(userDTO.phone(), userDTO.name(), userId)){
            throw new IllegalArgumentException("존재하는 사람입니다.");
        }
        Users users = userRepository.findById(userId).orElseThrow();
        users.update(userDTO.name(), userDTO.phone(), userDTO.prayerNote(), userDTO.picture());
        Users user = userRepository.save(users);
        return new UserDTO(user.getId(), user.getName(), user.getPhone(), user.getPrayerNote(),
            users.getPicture());
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
