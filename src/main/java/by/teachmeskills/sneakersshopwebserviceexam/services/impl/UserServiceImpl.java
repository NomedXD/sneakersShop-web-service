package by.teachmeskills.sneakersshopwebserviceexam.services.impl;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Order;
import by.teachmeskills.sneakersshopwebserviceexam.domain.User;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CategoryDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.LoginResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.RegistrationResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.UserConverter;
import by.teachmeskills.sneakersshopwebserviceexam.enums.EshopConstants;
import by.teachmeskills.sneakersshopwebserviceexam.enums.RequestParamsEnum;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVExportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVImportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.NoSuchUserException;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.UserRepository;
import by.teachmeskills.sneakersshopwebserviceexam.services.CategoryService;
import by.teachmeskills.sneakersshopwebserviceexam.services.OrderService;
import by.teachmeskills.sneakersshopwebserviceexam.services.UserService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final OrderService orderService;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CategoryService categoryService, @Lazy OrderService orderService, @Lazy UserConverter userConverter) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.orderService = orderService;
        this.userConverter = userConverter;
    }

    @Override
    public UserDto create(UserDto userDto) throws EntityOperationException {
        return userConverter.toDto(userRepository.create(userConverter.fromDto(userDto)));
    }

    @Override
    public List<UserDto> read() throws EntityOperationException {
        return userRepository.read().stream().map(userConverter::toDto).toList();
    }

    @Override
    public UserDto update(UserDto userDto) throws EntityOperationException {
        return userConverter.toDto(userRepository.update(userConverter.fromDto(userDto)));
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        userRepository.delete(id);
    }

    @Override
    public UserDto updateAccountData(UserDto updatedUserFields, UserDto userDto) throws EntityOperationException {
        Map<String, String> params = new HashMap<>();
        params.put(RequestParamsEnum.MOBILE.getValue(), updatedUserFields.getMobile());
        params.put(RequestParamsEnum.STREET.getValue(), updatedUserFields.getStreet());
        params.put(RequestParamsEnum.ACCOMMODATION_NUMBER.getValue(), updatedUserFields.getAccommodationNumber());
        params.put(RequestParamsEnum.FLAT_NUMBER.getValue(), updatedUserFields.getFlatNumber());
        setInputs(params, userDto);
        updatedUserFields = UserDto.builder().id(userDto.getId()).mail(userDto.getMail()).password(userDto.getPassword()).
                name(userDto.getName()).surname(userDto.getSurname()).date(userDto.getDate()).currentBalance(userDto.getCurrentBalance()).
                mobile(params.get(RequestParamsEnum.MOBILE.getValue())).street(params.get(RequestParamsEnum.STREET.getValue())).
                accommodationNumber(params.get(RequestParamsEnum.ACCOMMODATION_NUMBER.getValue())).
                flatNumber(params.get(RequestParamsEnum.FLAT_NUMBER.getValue())).build();
        User dbUser = userConverter.fromDto(updatedUserFields);
        dbUser.setOrders(userRepository.getUserOrders(userDto.getId()));
        userDto = userConverter.toDto(userRepository.update(dbUser));
        return userDto;
    }

    private void setInputs(Map<String, String> params, UserDto user) {
        for (var entry : params.entrySet()) {
            if (entry.getValue() == null || entry.getValue().isEmpty()) {
                switch (entry.getKey()) {
                    case "mobile" -> entry.setValue(user.getMobile());
                    case "street" -> entry.setValue(user.getStreet());
                    case "accommodationNumber" -> entry.setValue(user.getAccommodationNumber());
                    case "flatNumber" -> entry.setValue(user.getFlatNumber());
                }
            }
        }
    }

    @Override
    public ResponseEntity<LoginResponseWrapperDto> logIn(UserDto userDto) throws EntityOperationException {
        User loggedUser = userRepository.getUserByCredentials(userDto.getMail(), userDto.getPassword());
        if (loggedUser != null) {
            // Здесь было сохранение пользователя в сессию //
            return new ResponseEntity<>(new LoginResponseWrapperDto(userConverter.toDto(loggedUser),
                    categoryService.read()), HttpStatus.OK);
        } else {
            throw new NoSuchUserException("Wrong email or password. Try again");
        }
    }

    @Override
    public ResponseEntity<RegistrationResponseWrapperDto> register(UserDto userDto, String repeatPassword) throws EntityOperationException {
//        if (!bindingResult.hasErrors() && ValidatorUtils.validatePasswordMatching(user.getPassword(), repeatPassword)) {
//
//        }`
        User user = userRepository.create(User.builder().mail(userDto.getMail()).password(userDto.getPassword()).name(userDto.getName()).
                surname(userDto.getSurname()).date(userDto.getDate()).currentBalance(0f).orders(new ArrayList<>()).build());
        return new ResponseEntity<>(new RegistrationResponseWrapperDto(userConverter.toDto(user),
                categoryService.read()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CategoryDto>> checkIfLoggedInUser(UserDto userDto) throws EntityOperationException {
        if (userDto != null) {
            return new ResponseEntity<>(categoryService.read(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public User getUserById(Integer id) throws EntityOperationException {
        return userRepository.getUserById(id);
    }

    @Override
    public ResponseEntity<String> exportUserOrders(Integer userId) throws CSVExportException {
        writeCsv(userId);
        return new ResponseEntity<>(EshopConstants.successfulExportMessage, HttpStatus.OK);
    }

    private void writeCsv(Integer userId) throws CSVExportException {
        try (Writer writer = new FileWriter(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + "/orders.csv")) {
            StatefulBeanToCsv<OrderDto> sbc = new StatefulBeanToCsvBuilder<OrderDto>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            sbc.write(userConverter.toDto(userRepository.getUserById(userId)).getOrders());
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new CSVExportException(EshopConstants.errorOrdersExportMessage);
        }
    }

    @Override
    public ResponseEntity<List<OrderDto>> importUserOrders(MultipartFile file) throws CSVImportException {
        List<OrderDto> orderDtoList = parseCsv(file);
        if (Optional.ofNullable(orderDtoList).isPresent()) {
            orderDtoList.forEach(orderService::create);
            return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }

    private List<OrderDto> parseCsv(MultipartFile file) throws CSVImportException {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<OrderDto> csvToBean = new CsvToBeanBuilder<OrderDto>(reader)
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withType(OrderDto.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                return csvToBean.parse();
            } catch (IOException e) {
                throw new CSVImportException(EshopConstants.errorOrdersImportMessage);
            }
        } else {
            throw new CSVImportException(EshopConstants.errorFileNullMessage);
        }
    }
}
