package skybooker.server.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.VolDTO;
import skybooker.server.entity.Vol;
import skybooker.server.service.VolService;

@RestController
@RequestMapping("/api/v1/vol")
public class VolController {
    @Autowired
    private VolService volService;

    @GetMapping("/{id}")
    public ResponseEntity<VolDTO> vol(@PathVariable long id){
        Vol vol =volService.findById(id);
        if(vol == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(new VolDTO(vol));
        }
    }
    @PutMapping("/")
    public ResponseEntity<VolDTO> updateVol(@PathVariable long id, @RequestBody @Valid VolDTO volDTO){
        Vol vol = volService.findById(id);
        if(vol == null){
            return ResponseEntity.notFound().build();
        }else{
            vol.setDateDepart(volDTO.getDateDepart());
            vol.setHeureDepart(volDTO.getHeureDepart());
            vol.setDateArrive(volDTO.getDateArrive());
            vol.setHeureArrive(volDTO.getHeureArrive());
            vol.setEtat(volDTO.getEtat());
            vol.setPrix(volDTO.getPrix());

            Vol updatedVol = volService.update(vol);
            return ResponseEntity.ok(new VolDTO(updatedVol));
        }

    }

    @DeleteMapping("/")
    public ResponseEntity<VolDTO> deleteVol(@PathVariable long id){
        Vol vol = volService.findById(id);
        if(vol == null){
            return ResponseEntity.notFound().build();
        }else{
            volService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }
}
