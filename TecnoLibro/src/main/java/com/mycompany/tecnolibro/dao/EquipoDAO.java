
package com.mycompany.tecnolibro.dao;

import com.mycompany.tecnolibro.model.Equipo;
import java.util.List;

public interface EquipoDAO {
    
    List<Equipo> listar();
    
    void agregar(Equipo nuevo);
    
    void eliminar(String codigo);
    
}
