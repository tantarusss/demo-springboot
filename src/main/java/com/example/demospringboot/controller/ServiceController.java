package com.example.demospringboot.controller;

import com.example.demospringboot.IndexValueMapping;
import com.example.demospringboot.Zipcode;
import com.example.demospringboot.service.ZipcodeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@SecurityRequirement(name = "basicAuth")
@RequestMapping(value = "/api")
public class ServiceController {
    @Autowired
    private final ZipcodeService zipcodeService;
    private static final String template = "Hello, %s!";
    private static final String tableRowTemplate = """
            <tr>
            <td>%s</td>
            <td>%s</td>
            </tr>
            """;
    private static List<String> list = new ArrayList<>(List.of(new String[]{"Bob", "John", "Claire"}));
    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);
    public ServiceController() {
        zipcodeService = new ZipcodeService();
    }

    @GetMapping(value = "/greeting", produces = MediaType.TEXT_PLAIN_VALUE)
    public String greeting(@RequestParam(value = "value", defaultValue = "World") String name) {
        return String.format(template, name);
    }

    @GetMapping(value = "/object", produces = MediaType.APPLICATION_JSON_VALUE)
    public IndexValueMapping object(@RequestParam(value = "value", defaultValue = "Bob") String name) {
        return new IndexValueMapping(981, name);
    }
    @GetMapping(value = "/zipcodes", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String zipcodesAsHTML() throws IOException {
        StringBuilder html = new StringBuilder();
        Map<String, Integer> zipcodes = zipcodeService.getZipcodes();
        html.append("<html>\n");
        html.append("<header><title>Zipcodes</title><header>");
        html.append("<body>\n");
        html.append("<table>");
        html.append("<tr>");
        html.append("<th>Area</th>");
        html.append("<th>Zipcode</th>");
        html.append("</tr>");
        for (Map.Entry<String, Integer> entry : zipcodes.entrySet()) {
            html.append(String.format(tableRowTemplate, entry.getKey(), entry.getValue().toString()));
        }
        html.append("""
            </table>
            </body>
            </html>
            """);
        return html.toString();
    }
    @PostMapping(value = "/zipcodes/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Integer>> addToZipcodes(@RequestBody Zipcode zipcode) {
        try {
            zipcodeService.newZipcode(zipcode);
            return new ResponseEntity<>(zipcodeService.getZipcodes(), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping(value = "/zipcodes/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Integer>> updateZipcode(@RequestBody Zipcode zipcode) {
        try {
            zipcodeService.update(zipcode);
            return new ResponseEntity<>(zipcodeService.getZipcodes(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping(value = "/zipcodes/delete/{area}")
    public ResponseEntity<HttpStatus> deleteZipcode(@PathVariable(value = "area") String area) {
        zipcodeService.delete(area);
        logger.info("Deleted: " + area);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/body", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public IndexValueMapping body(@RequestBody IndexValueMapping myData) {
        logger.info("Recieved: " + myData.toString());
        IndexValueMapping resp = new IndexValueMapping(myData.getIndex() * myData.getIndex(),myData.getValue() + " smith");
        logger.info("Sending out: " + resp.toString());
        return resp;
    }
    @PostMapping(value = "/list/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> addToList(@RequestBody String name) {
        list.add(name);
        logger.info("Added " + name + " to list");
        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }
    @PutMapping(value = "/list/change", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> changeList(@RequestBody IndexValueMapping indexValueMapping) {
        try {
            String oldName = list.get(indexValueMapping.getIndex());
            String newName = indexValueMapping.getValue();
            list.set(indexValueMapping.getIndex(), indexValueMapping.getValue());
            logger.info("Changed name " + oldName + " at index " + indexValueMapping.getIndex() + " to " + newName);
            return new ResponseEntity<>(list, HttpStatus.CREATED);
        } catch (IndexOutOfBoundsException e) {
            // kann man Exception e hier mitgeben?
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/list/get", produces = MediaType.TEXT_HTML_VALUE)
    public String getListAsHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<html>\n");
        html.append("<header><title>List of names</title><header>");
        html.append("<body>\n");
        html.append("<table>");
        html.append("<tr>");
        html.append("<th>Name</th>");
        html.append("</tr>");
        for (String name: list) {
            html.append(String.format("""
                    <tr>
                    <td>%s</td>
                    </tr>
                    """
                    , name));
        }
        html.append("""
            </table>
            </body>
            </html>
            """);
        return html.toString();
    }
    @DeleteMapping(value = "/list/delete/{name}")
    public List<String> deleteFromList(@PathVariable(value = "name") String name) {
        list.remove(name);
        return list;
    }
}
