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

    @GetMapping("/{volId}/{classeId}")
    public ResponseEntity<PriceDTO> getPrice(@PathVariable long volId, @PathVariable long classeId) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolDTO> vol(@PathVariable long id){
        Vol vol = volService.findById(id);
        if(vol == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(new VolDTO(vol));
        }
    }

    @PostMapping("/")
    public ResponseEntity<VolDTO> addVol(@RequestBody @Valid VolDTO volDTO){
        Vol vol = volService.createDTO(volDTO);
        if(vol == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new VolDTO(vol));
        }
    }

    @PutMapping("/")
    public ResponseEntity<VolDTO> updateVol(@RequestBody @Valid VolDTO volDTO){
        Vol updatedVol = volService.updateDTO(volDTO);
        if(updatedVol == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new VolDTO(updatedVol));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVol(@PathVariable long id){
        volService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
