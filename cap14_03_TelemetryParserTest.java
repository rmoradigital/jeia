import org.junit.jupiter.api.Test;
import org.junit.jupiter.api::DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class TelemetryParserTest {

    private final TelemetryParser parser = new TelemetryParser();

    @Test
    @DisplayName("Debe procesar correctamente una trama válida")
    void testParseValidTrama() throws TelemetryParser.ChecksumException {
        // Trama: [0xAA] [0x01] [0x90, 0x25] [Checksum]
        // Valor: 0x2590 (9616) -> 96.16 C
        // Checksum: (0xAA + 0x01 + 0x90 + 0x25) = 0x160 -> 0x60
        byte[] tramaValida = {(byte) 0xAA, (byte) 0x01, (byte) 0x90, (byte) 0x25, (byte) 0x60};
        
        double resultado = parser.parseTemperature(tramaValida);
        assertEquals(96.16, resultado, 0.001, "La temperatura procesada es incorrecta");
    }

    @Test
    @DisplayName("Debe lanzar ChecksumException cuando el checksum es incorrecto")
    void testInvalidChecksum() {
        // Trama con checksum erróneo
        byte[] tramaErronea = {(byte) 0xAA, 0x01, 0x00, 0x00, (byte) 0xFF};
        
        assertThrows(TelemetryParser.ChecksumException.class, () -> {
            parser.parseTemperature(tramaErronea);
        }, "Se esperaba una excepción de Checksum");
    }

    @Test
    @DisplayName("Debe lanzar IllegalArgumentException ante encabezado incorrecto")
    void testInvalidHeader() {
        byte[] tramaSinHeader = {0x00, 0x1, 0x2, 0x3, 0x4};
        
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parseTemperature(tramaSinHeader);
        });
    }
}
