package skybooker.server.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.PriceDTO;
import skybooker.server.DTO.VolDTO;
import skybooker.server.entity.Vol;
import skybooker.server.service.VolService;

@RestController
@RequestMapping("/api/v1/vol")
public class VolController {
    @Autowired
    private VolService volService;

    @GetMapping("/{volId}/{classeId}")
public ResponseEntity<PriceDTO> getPrice(
    @PathVariable long volId, 
    @PathVariable long classeId) {
    
    Double calculatedPrice = volService.calculatePrice(volId, classeId);
    
    if (calculatedPrice == null) {
        return ResponseEntity.notFound().build();
    }
    
    PriceDTO response = new PriceDTO(calculatedPrice.floatValue());
    return ResponseEntity.ok(response);
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
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<VolDTO> addVol(@RequestBody @Valid VolDTO volDTO){
        Vol vol = volService.createDTO(volDTO);
        if(vol == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new VolDTO(vol));
        }
    }

    @PutMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<VolDTO> updateVol(@RequestBody @Valid VolDTO volDTO){
        Vol updatedVol = volService.updateDTO(volDTO);
        if(updatedVol == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new VolDTO(updatedVol));
        }
    }

    @DeleteMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<Void> deleteVol(@PathVariable long id){
        volService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
