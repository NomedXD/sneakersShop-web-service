package by.teachmeskills.sneakersshopwebserviceexam.services.impl;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Role;
import by.teachmeskills.sneakersshopwebserviceexam.domain.User;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderProductDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.GetAccountResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.LoginResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.UserConverter;
import by.teachmeskills.sneakersshopwebserviceexam.enums.EshopConstants;
import by.teachmeskills.sneakersshopwebserviceexam.enums.RequestParamsEnum;
import by.teachmeskills.sneakersshopwebserviceexam.enums.UserRoleEnum;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVExportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVImportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.NoSuchUserException;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.UserRepository;
import by.teachmeskills.sneakersshopwebserviceexam.services.CategoryService;
import by.teachmeskills.sneakersshopwebserviceexam.services.OrderService;
import by.teachmeskills.sneakersshopwebserviceexam.services.UserService;
import by.teachmeskills.sneakersshopwebserviceexam.utils.OrderProductDtoConverter;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    private final PasswordEncoder encoder;
    private final OrderProductDtoConverter orderProductDtoConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           CategoryService categoryService,
                           @Lazy OrderService orderService,
                           @Lazy UserConverter userConverter,
                           PasswordEncoder passwordEncoder,
                           OrderProductDtoConverter orderProductDtoConverter) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.orderService = orderService;
        this.userConverter = userConverter;
        this.encoder = passwordEncoder;
        this.orderProductDtoConverter = orderProductDtoConverter;
    }

    @Override
    public UserDto create(UserDto userDto) {
        return userConverter.toDto(userRepository.save(userConverter.fromDto(userDto)));
    }

    @Override
    public List<UserDto> read() {
        return userRepository.findAll().stream().map(userConverter::toDto).toList();
    }

    @Override
    public UserDto update(UserDto userDto) {
        return userConverter.toDto(userRepository.save(userConverter.fromDto(userDto)));
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<GetAccountResponseWrapperDto> getAccount(UserDto userDto, Integer currentPage, Integer pageSize) {
        return new ResponseEntity<>(new GetAccountResponseWrapperDto(orderService.getPaginatedOrders(currentPage, pageSize, userDto.getId()), userDto), HttpStatus.OK);
    }

    @Override
    public UserDto updateAccountData(UserDto updatedUserFields, UserDto userDto) {
        Map<String, String> params = new HashMap<>();
        params.put(RequestParamsEnum.MOBILE.getValue(), updatedUserFields.getMobile());
        params.put(RequestParamsEnum.STREET.getValue(), updatedUserFields.getStreet());
        params.put(RequestParamsEnum.ACCOMMODATION_NUMBER.getValue(), updatedUserFields.getAccommodationNumber());
        params.put(RequestParamsEnum.FLAT_NUMBER.getValue(), updatedUserFields.getFlatNumber());
        setInputs(params, userDto);
        updatedUserFields = UserDto.builder().id(userDto.getId()).mail(userDto.getMail()).password(userDto.getPassword()).
                name(userDto.getName()).surname(userDto.getSurname()).date(userDto.getDate()).
                mobile(params.get(RequestParamsEnum.MOBILE.getValue())).street(params.get(RequestParamsEnum.STREET.getValue())).
                accommodationNumber(params.get(RequestParamsEnum.ACCOMMODATION_NUMBER.getValue())).
                flatNumber(params.get(RequestParamsEnum.FLAT_NUMBER.getValue())).build();
        User dbUser = userConverter.fromDto(updatedUserFields);
        dbUser.setOrders(userRepository.findUserById(userDto.getId()).orElseThrow(() -> new NoSuchUserException("No user with such id")).getOrders());
        userDto = userConverter.toDto(userRepository.save(dbUser));
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
    public ResponseEntity<LoginResponseWrapperDto> logIn(UserDto userDto) {
        Optional<User> loggedUser = userRepository.findUserByMailAndPassword(userDto.getMail(), userDto.getPassword());
        if (loggedUser.isPresent()) {
            // Здесь было сохранение пользователя в сессию //
            return new ResponseEntity<>(new LoginResponseWrapperDto(userConverter.toDto(loggedUser.get()),
                    categoryService.getPaginatedCategories(1, EshopConstants.MIN_PAGE_SIZE)), HttpStatus.OK);
        } else {
            throw new NoSuchUserException("Wrong email or password. Try again");
        }
    }

    @Override
    public ResponseEntity<UserDto> register(UserDto userDto, String repeatPassword) {
        User user = userRepository.save(User.builder().mail(userDto.getMail()).password(encoder.encode(userDto.getPassword())).name(userDto.getName()).
                surname(userDto.getSurname()).date(userDto.getDate()).orders(new ArrayList<>()).roles(List.of(Role.builder().id(2).name(UserRoleEnum.USER.name()).build())).build());
        return new ResponseEntity<>(userConverter.toDto(user), HttpStatus.OK);
    }

    @Override
    public Boolean checkIfLoggedInUser(UserDto userDto) {
        return userDto != null;
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findUserById(id).orElseThrow(() -> new NoSuchUserException("No user with id " + id));
    }

    @Override
    public ResponseEntity<InputStreamResource> exportUserOrders(Integer userId) throws CSVExportException {
        return writeCsv(userId);
    }

    private ResponseEntity<InputStreamResource> writeCsv(Integer userId) throws CSVExportException {
        List<OrderDto> orderDtoList = userConverter.toDto(userRepository.findUserById(userId).
                orElseThrow(() -> new NoSuchUserException("No user with id " + userId))).getOrders();
        List<OrderProductDto> orderProductDtoList = orderProductDtoConverter.convertInto(orderDtoList);
        try (Writer ordersProductsWriter = Files.newBufferedWriter(Paths.get(EshopConstants.resourcesFilePath + "user_" + userId + "_orders_products.csv"))) {
            StatefulBeanToCsv<OrderProductDto> ordersProductsSbc = new StatefulBeanToCsvBuilder<OrderProductDto>(ordersProductsWriter)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            ordersProductsSbc.write(orderProductDtoList);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(EshopConstants.resourcesFilePath + "user_" + userId + "_orders_products.csv"));
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "user_" + userId + "_orders_products.csv")
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(resource);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new CSVExportException(EshopConstants.errorOrdersExportMessage);
        }
    }

    @Override
    public ResponseEntity<List<OrderDto>> importUserOrders(MultipartFile file) throws CSVImportException {
        List<OrderDto> orderDtoList = parseCsv(file);
        orderDtoList.forEach(orderDto -> {
            orderDto.setId(0); // Чтобы создавался новый заказ, а не обновлялся этот же, если что - удалить
            orderDto.setId(orderService.create(orderDto).getId());
        });
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }

    private List<OrderDto> parseCsv(MultipartFile file) throws CSVImportException {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader ordersProdcutsReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<OrderProductDto> ordersProductsCtb = new CsvToBeanBuilder<OrderProductDto>(ordersProdcutsReader)
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withType(OrderProductDto.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                List<OrderProductDto> orderProductDtoList = ordersProductsCtb.parse();
                return orderProductDtoConverter.convertFrom(orderProductDtoList);
            } catch (IOException e) {
                throw new CSVImportException(EshopConstants.errorOrdersImportMessage);
            }
        } else {
            throw new CSVImportException(EshopConstants.errorFileNullMessage);
        }
    }
}
