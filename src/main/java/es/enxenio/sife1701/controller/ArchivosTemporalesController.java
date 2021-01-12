package es.enxenio.sife1701.controller;

import es.enxenio.sife1701.controller.custom.util.MessageJSON;
import es.enxenio.sife1701.controller.util.CodeUtil;
import es.enxenio.sife1701.util.ConstantesModel;
import es.enxenio.sife1701.util.ConstantesRest;
import es.enxenio.sife1701.util.upload.FileUploadHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;

@RestController
@RequestMapping(value = ConstantesRest.URL_BASE)
@PreAuthorize(ConstantesRest.PERMIT_ALL)
public class ArchivosTemporalesController {

    @Autowired
    private FileUploadHelper fileUploadHelper;

    @RequestMapping(value = "/archivos-temporales")
    public ResponseEntity<MessageJSON> subirArchivoTemporal(@RequestParam("file") MultipartFile file,
                                                            @RequestParam("ext") String ext) {

        String[] extensions = ConstantesModel.EXTENSIONES_VALIDAS;
        if (CodeUtil.notEmpty(ext)) {
            extensions = ext.replace(".", "").replace(" ", "").split(",");
            Arrays.asList(extensions).retainAll(Arrays.asList(ConstantesModel.EXTENSIONES_VALIDAS));
        }

        if (fileUploadHelper.eExtensionValida(file.getOriginalFilename(), extensions)) {
            File directorioTemporal = fileUploadHelper.obtenerDirectorio(ConstantesModel.URL_CARPETA_ARCHIVOS_TEMPORALES);
            String archivoTemporal = fileUploadHelper.guardarArchivoTemporal(file, directorioTemporal,
                ConstantesModel.URL_CARPETA_ARCHIVOS_TEMPORALES);
            return new ResponseEntity<>(new MessageJSON(archivoTemporal), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageJSON("invalid extension"), HttpStatus.BAD_REQUEST);
        }
    }

}
