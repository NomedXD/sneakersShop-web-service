package by.teachmeskills.sneakersshopwebserviceexam.controllers.complex_controllers_training;

import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.UpdateUserRequestWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.ValidationException;
import by.teachmeskills.sneakersshopwebserviceexam.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Tag(name = "account", description = "Account Endpoints")
@RestController
@RequestMapping("/account")
public class AccountController {
    private final UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    /*
        Передаю конкретно два объекта, так как не предполагается, что в updatedUserFields не будет id(он не передается с формы),
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
    @Operation(
            summary = "Update user",
            description = "Update user data by form",
            tags = {"account"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful update",
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Request UpdateUserRequestWrapperDto object validation error - server error"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateAccountData(@Valid @RequestBody UpdateUserRequestWrapperDto requestBody, BindingResult result) throws EntityOperationException {
        if (!result.hasErrors()) {
            return new ResponseEntity<>(userService.updateAccountData(requestBody.getUpdatedUserFields(), requestBody.getUser()), HttpStatus.OK);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }
}
