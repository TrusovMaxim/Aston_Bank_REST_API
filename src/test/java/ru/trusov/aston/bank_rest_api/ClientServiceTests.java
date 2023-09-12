package ru.trusov.aston.bank_rest_api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.trusov.aston.bank_rest_api.exception.ClientNotFoundException;
import ru.trusov.aston.bank_rest_api.model.Client;
import ru.trusov.aston.bank_rest_api.request.TopUpOrWrittenOffBankAccountRequest;
import ru.trusov.aston.bank_rest_api.service.ClientServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.only;

@ExtendWith(MockitoExtension.class)
class ClientServiceTests {

    @Mock
    ClientServiceImpl mockService;

    @Test
    @DisplayName("Получение клиента из базы данных через имя и пинкод")
    public void returnMockClientByNameAndPincode() {
        var client = new Client("Maxim", 1234);
        var clientRequest = new TopUpOrWrittenOffBankAccountRequest();
        Mockito.doReturn(client).when(mockService).getClientByNameAndPincode(clientRequest);
        assertEquals(client, mockService.getClientByNameAndPincode(clientRequest));
    }

    @Test
    @DisplayName("Получение исключения о том, что клиент не найден")
    public void returnExceptionFromServiceByNameAndPincode() {
        var clientRequest = new TopUpOrWrittenOffBankAccountRequest();
        Mockito.when(mockService.getClientByNameAndPincode(clientRequest)).thenThrow(new ClientNotFoundException());
        assertThrows(ClientNotFoundException.class, () -> mockService.getClientByNameAndPincode(clientRequest));
    }

    @Test
    @DisplayName("Метод получения клиента был вызван только один раз")
    public void returnExceptionFromServiceOnId4() {
        var clientRequest = new TopUpOrWrittenOffBankAccountRequest();
        var method = mockService.getClientByNameAndPincode(clientRequest);
        Mockito.verify(mockService, only()).getClientByNameAndPincode(clientRequest);
    }
}