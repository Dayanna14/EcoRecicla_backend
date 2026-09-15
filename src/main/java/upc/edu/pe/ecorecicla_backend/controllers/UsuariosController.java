package upc.edu.pe.ecorecicla_backend.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import upc.edu.pe.ecorecicla_backend.dtos.UsuariosDTO;
import upc.edu.pe.ecorecicla_backend.entities.Rol;
import upc.edu.pe.ecorecicla_backend.entities.Usuarios;
import upc.edu.pe.ecorecicla_backend.exceptions.ResourceNotFoundException;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IRolService;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IUsuarioService;

import java.net.URI;

public class UsuariosController {
    private final IUsuarioService uS;
    private final ModelMapper modelMapper;
    private final IRolService rS;

    public UsuariosController(IUsuarioService uS, ModelMapper modelMapper, IRolService rS) {
        this.uS = uS;
        this.modelMapper = modelMapper;
        this.rS = rS;
    }

//    @PostMapping
//    public ResponseEntity<UsuariosDTO> registrar(@Valid @RequestBody UsuariosDTO dto){
//        Rol rol=rS.listId(dto.getIdRol())
//                .orElseThrow(()->
//                        new ResourceNotFoundException(
//                                "El rol no existe!."
//                        ));
//        Usuarios u=modelMapper.map(dto,Usuarios.class);
//        u.setMovie(movie);
//        uS.insert(u);
//        UsuariosDTO responseDTO=modelMapper.map(u,UsuariosDTO.class);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(u.getIdUsuarios())
//                .toUri();
//
//        return ResponseEntity.created(location).body(responseDTO);
//    }
}
