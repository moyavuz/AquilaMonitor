package tr.bel.pendik.aquilamonitor.controller.management;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tr.bel.pendik.aquilamonitor.data.ResponsibleEntity;
import tr.bel.pendik.aquilamonitor.repository.ResponsibleRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/responsible")
public class ResponsibleController {

    @Autowired
    ResponsibleRepository repository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ResponseEntity<List<ResponsibleEntity>> listAll() {
        List<ResponsibleEntity> list = new ArrayList<ResponsibleEntity>();
        list = repository.findAll();
        if (list == null)
            return new ResponseEntity<List<ResponsibleEntity>>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<ResponsibleEntity>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ResponseEntity<ResponsibleEntity> getById(@PathVariable("id") String id) {
        ResponsibleEntity entity = new ResponsibleEntity();
        entity = repository.findOne(Long.valueOf(id));
        if (entity == null)
            return new ResponseEntity<ResponsibleEntity>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<ResponsibleEntity>(entity, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResponsibleEntity> insert(
            @RequestBody ResponsibleEntity app) {
        ResponsibleEntity afterSave = repository.save(app);
        ResponseEntity<ResponsibleEntity> respEntity;
        if (afterSave.getId() > -1)
            respEntity = new ResponseEntity<ResponsibleEntity>(afterSave, HttpStatus.CREATED);
        else
            respEntity = new ResponseEntity<ResponsibleEntity>(app, HttpStatus.CONFLICT);
        return respEntity;
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResponsibleEntity> update(@RequestBody ResponsibleEntity app) {
        ResponsibleEntity afterSave = repository.save(app);
        ResponseEntity<ResponsibleEntity> respEntity;
        if (afterSave.getId() > -1)
            respEntity = new ResponseEntity<ResponsibleEntity>(afterSave, HttpStatus.OK);
        else
            respEntity = new ResponseEntity<ResponsibleEntity>(app, HttpStatus.NOT_FOUND);
        return respEntity;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> delete(
            @PathVariable("id") String deleteId) {
        ResponseEntity<Void> respEntity = null;
        if (StringUtils.isBlank(deleteId) && !StringUtils.isNumeric(deleteId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repository.delete(Long.valueOf(deleteId));

        boolean result = repository.exists(Long.valueOf(deleteId));

        if (!result)
            respEntity = new ResponseEntity<>(HttpStatus.OK);
        else {
            respEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return respEntity;
    }
}