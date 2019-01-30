package pettyCash

import com.finance.pettyCash.PettyCashApplication
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = PettyCashApplication)
class JsonSchemmaSpec extends BaseTest {
    def "Obtain Data Json Schemma usintg rest template"() {
        given: "I have a form and section code"
        def form = formCode
        def section = sectionCode
        def result = results
        when:
        def response
        try {
            jsonSchemaService.getSchemmaData(formCode, sectionCode)
            response = "SUCCESS"
        } catch (Exception ve) {
            response = getResponseError(ve.errorServer.toString(), result)
        }
        then:
        response == result.toString()
        where:
        formCode                           | sectionCode                        | results
        "46837de39e5fb779a60255348d3"      | "b0fc5131d9539fe7f52aab66f0"       | ["INVALID_FORM_DEFINITION_CODE"]
        "46837de39e5fb779a60255348d342339" | null                               | ["INVALID_FORM_SECTION"]
        null                               | "b0fc5131d9539fe7f52aab66f0bacea2" | ["INVALID_FORM_DEFINITION_CODE"]
        null                               | null                               | ["INVALID_FORM_DEFINITION_CODE"]
        "46837de39e5fb779a60255348d342339" | "b0fc5131d9539fe7f52aab66f0bacea2" | "SUCCESS"
    }
}
