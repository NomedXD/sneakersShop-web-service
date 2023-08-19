package by.teachmeskills.sneakersshopwebserviceexam.controllers.complex_controllers_training;

import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.UpdateUserRequestWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import by.teachmeskills.sneakersshopwebserviceexam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    /*
        Передаю конкретно два объекта, так как не предполагается, что в updatedUserFields будет id(она не передается с формы),
        по которому будет делаться обновление в базе. Для этого нужна и сущность, которую обновлять.
        Скорее всего, запрос должен приходить в первый веб сервис, который извлечет User из условной сессии,
        а потом этот сервис вызывает метод вот этого сервиса.
        А вообще, есть два нормальных варианта в RESTfull: 1) хранить все данный в куки, 2) разделить храненение между
        клиентом и сервером.
        1) Так как размер куки мал + данные хранятся в виде строк, которые нужно парсить, это является худшим из
        двух вариантов, однако это все еще позволяет установить балансировщик нагрузки для серверов, так как данные
        клиентов разделены
        2) Надежным вариантом является разделение сессии и куки в Rest. При посещении сервиса, для клиента
        открывается сессия с уникальным идентификатором. Ее можно захешировать для меньшего размера и передать
        клиенту в качестве куки. В то же время, для сессии используется хранилище сессий. Это может быть как база
        данных, так и хранилища по типу memcached. Например, в бд можно хранить сессию в таблице, где первичным ключом
        является значение хеш функции от идентификатора сессии. Однако более быстрым вариантом является хранение
        сессий прямо в оперативной памяти(memcached).

        Здесь и дальше параметры сессии передаются прямо в RequestBody, а также возвращаются в ответе для наглядности
     */
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateAccountData(@RequestBody UpdateUserRequestWrapperDto updateUserRequestWrapperDto) throws EntityOperationException {
        return new ResponseEntity<>(userService.updateAccountData(updateUserRequestWrapperDto.getUpdatedUserFields(), updateUserRequestWrapperDto.getUser()), HttpStatus.OK);
    }
}
