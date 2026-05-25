package com.empresa.api.infrastructure.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class AppMessages {

    // ===== GLOBAL =====
    @Value("${error.validacion}")          private String errorValidacion;
    @Value("${error.negocio}")             private String errorNegocio;
    @Value("${error.duplicado.bd}")        private String errorDuplicadoBd;
    @Value("${error.duplicado.bd.detalle}")private String errorDuplicadoBdDetalle;
    @Value("${error.no.encontrado}")       private String errorNoEncontrado;
    @Value("${error.operacion.fallida}")   private String errorOperacionFallida;

    // ===== FACULTAD =====
    @Value("${facultad.nombre.obligatorio}") private String facultadNombreObligatorio;
    @Value("${facultad.nombre.minimo}")      private String facultadNombreMinimo;
    @Value("${facultad.decano.obligatorio}") private String facultadDecanoObligatorio;
    @Value("${facultad.duplicado}")          private String facultadDuplicado;
    @Value("${facultad.lista.vacia}")        private String facultadListaVacia;
    @Value("${facultad.no.encontrada}")      private String facultadNoEncontrada;
    @Value("${facultad.creada}")             private String facultadCreada;
    @Value("${facultad.actualizada}")        private String facultadActualizada;
    @Value("${facultad.eliminada}")          private String facultadEliminada;
    @Value("${facultad.encontrada}")         private String facultadEncontrada;
    @Value("${facultad.lista}")              private String facultadLista;

    // ===== PROGRAMA =====
    @Value("${programa.nombre.obligatorio}")     private String programaNombreObligatorio;
    @Value("${programa.semestres.positivo}")      private String programaSemestresPositivo;
    @Value("${programa.semestres.maximo}")        private String programaSemestresMaximo;
    @Value("${programa.facultad.obligatorio}")    private String programaFacultadObligatorio;
    @Value("${programa.duplicado}")               private String programaDuplicado;
    @Value("${programa.lista.vacia}")             private String programaListaVacia;
    @Value("${programa.no.encontrado}")           private String programaNoEncontrado;
    @Value("${programa.facultad.no.encontrada}")  private String programaFacultadNoEncontrada;
    @Value("${programa.creado}")                  private String programaCreado;
    @Value("${programa.actualizado}")             private String programaActualizado;
    @Value("${programa.eliminado}")               private String programaEliminado;
    @Value("${programa.encontrado}")              private String programaEncontrado;
    @Value("${programa.lista}")                   private String programaLista;
    @Value("${programa.lista.facultad}")          private String programaListaFacultad;

    // ===== ASIGNATURA =====
    @Value("${asignatura.nombre.obligatorio}")    private String asignaturaNombreObligatorio;
    @Value("${asignatura.creditos.positivo}")     private String asignaturaCreditosPositivo;
    @Value("${asignatura.creditos.maximo}")       private String asignaturaCreditosMaximo;
    @Value("${asignatura.semestre.positivo}")     private String asignaturaSemestrePositivo;
    @Value("${asignatura.semestre.maximo}")       private String asignaturaSemestreMaximo;
    @Value("${asignatura.programa.obligatorio}")  private String asignaturaProgramaObligatorio;
    @Value("${asignatura.duplicado}")             private String asignaturaDuplicado;
    @Value("${asignatura.lista.vacia}")           private String asignaturaListaVacia;
    @Value("${asignatura.no.encontrada}")         private String asignaturaNoEncontrada;
    @Value("${asignatura.programa.no.encontrado}")private String asignaturaProgramaNoEncontrado;
    @Value("${asignatura.creada}")                private String asignaturaCreada;
    @Value("${asignatura.actualizada}")           private String asignaturaActualizada;
    @Value("${asignatura.eliminada}")             private String asignaturaEliminada;
    @Value("${asignatura.encontrada}")            private String asignaturaEncontrada;
    @Value("${asignatura.lista}")                 private String asignaturaLista;
    @Value("${asignatura.lista.programa}")        private String asignaturaListaPrograma;

    // ===== BOOK =====
    @Value("${book.titulo.obligatorio}")   private String bookTituloObligatorio;
    @Value("${book.anio.obligatorio}")     private String bookAnioObligatorio;
    @Value("${book.anio.minimo}")          private String bookAnioMinimo;
    @Value("${book.anio.maximo}")          private String bookAnioMaximo;
    @Value("${book.duplicado}")            private String bookDuplicado;
    @Value("${book.lista.vacia}")          private String bookListaVacia;
    @Value("${book.lista.vacia.autor}")    private String bookListaVaciaAutor;
    @Value("${book.no.encontrado}")        private String bookNoEncontrado;
    @Value("${book.autor.no.encontrado}")  private String bookAutorNoEncontrado;
    @Value("${book.creado}")               private String bookCreado;
    @Value("${book.actualizado}")          private String bookActualizado;
    @Value("${book.eliminado}")            private String bookEliminado;
    @Value("${book.encontrado}")           private String bookEncontrado;
    @Value("${book.lista}")                private String bookLista;
    @Value("${book.lista.autor}")          private String bookListaAutor;

    // ===== AUTHOR =====
    @Value("${author.nombre.obligatorio}")    private String authorNombreObligatorio;
    @Value("${author.nombre.minimo}")         private String authorNombreMinimo;
    @Value("${author.nacionalidad.obligatorio}") private String authorNacionalidadObligatorio;
    @Value("${author.duplicado}")             private String authorDuplicado;
    @Value("${author.lista.vacia}")           private String authorListaVacia;
    @Value("${author.no.encontrado}")         private String authorNoEncontrado;
    @Value("${author.creado}")                private String authorCreado;
    @Value("${author.actualizado}")           private String authorActualizado;
    @Value("${author.eliminado}")             private String authorEliminado;
    @Value("${author.encontrado}")            private String authorEncontrado;
    @Value("${author.lista}")                 private String authorLista;

    // ===== USER =====
    @Value("${user.datos.nulos}")          private String userDatosNulos;
    @Value("${user.email.obligatorio}")    private String userEmailObligatorio;
    @Value("${user.nombres.obligatorio}")  private String userNombresObligatorio;
    @Value("${user.apellidos.obligatorio}")private String userApellidosObligatorio;
    @Value("${user.edad.obligatorio}")     private String userEdadObligatorio;
    @Value("${user.validado}")             private String userValidado;
    @Value("${user.creado}")               private String userCreado;
    @Value("${user.error.validacion}")     private String userErrorValidacion;
    @Value("${user.email.no.encontrado}")  private String userEmailNoEncontrado;
    @Value("${user.total}")                private String userTotal;
    @Value("${user.lista}")                private String userLista;
    @Value("${user.encontrado}")           private String userEncontrado;
    @Value("${user.error.crear}")          private String userErrorCrear;

    // ===== POST =====
    @Value("${post.lista}")      private String postLista;
    @Value("${post.encontrado}") private String postEncontrado;
}
