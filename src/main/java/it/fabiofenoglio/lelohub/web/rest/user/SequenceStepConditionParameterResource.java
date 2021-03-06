package it.fabiofenoglio.lelohub.web.rest.user;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import it.fabiofenoglio.lelohub.security.AuthoritiesConstants;
import it.fabiofenoglio.lelohub.service.SequenceStepConditionParameterQueryService;
import it.fabiofenoglio.lelohub.service.SequenceStepConditionParameterService;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionParameterCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionParameterDTO;
import it.fabiofenoglio.lelohub.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link it.fabiofenoglio.lelohub.domain.SequenceStepConditionParameter}.
 */
@RestController
@RequestMapping("/api")
public class SequenceStepConditionParameterResource {

    private final Logger log = LoggerFactory.getLogger(SequenceStepConditionParameterResource.class);

    private static final String ENTITY_NAME = "sequenceStepConditionParameter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequenceStepConditionParameterService sequenceStepConditionParameterService;

    private final SequenceStepConditionParameterQueryService sequenceStepConditionParameterQueryService;

    public SequenceStepConditionParameterResource(SequenceStepConditionParameterService sequenceStepConditionParameterService, SequenceStepConditionParameterQueryService sequenceStepConditionParameterQueryService) {
        this.sequenceStepConditionParameterService = sequenceStepConditionParameterService;
        this.sequenceStepConditionParameterQueryService = sequenceStepConditionParameterQueryService;
    }

    /**
     * {@code POST  /sequence-step-condition-parameters} : Create a new sequenceStepConditionParameter.
     *
     * @param sequenceStepConditionParameterDTO the sequenceStepConditionParameterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequenceStepConditionParameterDTO, or with status {@code 400 (Bad Request)} if the sequenceStepConditionParameter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PostMapping("/sequence-step-condition-parameters")
    public ResponseEntity<SequenceStepConditionParameterDTO> createSequenceStepConditionParameter(@Valid @RequestBody SequenceStepConditionParameterDTO sequenceStepConditionParameterDTO) throws URISyntaxException {
        log.debug("REST request to save SequenceStepConditionParameter : {}", sequenceStepConditionParameterDTO);
        if (sequenceStepConditionParameterDTO.getId() != null) {
            throw new BadRequestAlertException("A new sequenceStepConditionParameter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SequenceStepConditionParameterDTO result = sequenceStepConditionParameterService.save(sequenceStepConditionParameterDTO);
        return ResponseEntity.created(new URI("/api/sequence-step-condition-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sequence-step-condition-parameters} : Updates an existing sequenceStepConditionParameter.
     *
     * @param sequenceStepConditionParameterDTO the sequenceStepConditionParameterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceStepConditionParameterDTO,
     * or with status {@code 400 (Bad Request)} if the sequenceStepConditionParameterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequenceStepConditionParameterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PutMapping("/sequence-step-condition-parameters")
    public ResponseEntity<SequenceStepConditionParameterDTO> updateSequenceStepConditionParameter(@Valid @RequestBody SequenceStepConditionParameterDTO sequenceStepConditionParameterDTO) throws URISyntaxException {
        log.debug("REST request to update SequenceStepConditionParameter : {}", sequenceStepConditionParameterDTO);
        if (sequenceStepConditionParameterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SequenceStepConditionParameterDTO result = sequenceStepConditionParameterService.save(sequenceStepConditionParameterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceStepConditionParameterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sequence-step-condition-parameters} : get all the sequenceStepConditionParameters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequenceStepConditionParameters in body.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @GetMapping("/sequence-step-condition-parameters")
    public ResponseEntity<List<SequenceStepConditionParameterDTO>> getAllSequenceStepConditionParameters(SequenceStepConditionParameterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SequenceStepConditionParameters by criteria: {}", criteria);
        Page<SequenceStepConditionParameterDTO> page = sequenceStepConditionParameterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sequence-step-condition-parameters/count} : count all the sequenceStepConditionParameters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @GetMapping("/sequence-step-condition-parameters/count")
    public ResponseEntity<Long> countSequenceStepConditionParameters(SequenceStepConditionParameterCriteria criteria) {
        log.debug("REST request to count SequenceStepConditionParameters by criteria: {}", criteria);
        return ResponseEntity.ok().body(sequenceStepConditionParameterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sequence-step-condition-parameters/:id} : get the "id" sequenceStepConditionParameter.
     *
     * @param id the id of the sequenceStepConditionParameterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequenceStepConditionParameterDTO, or with status {@code 404 (Not Found)}.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-step-condition-parameters/{id}")
    public ResponseEntity<SequenceStepConditionParameterDTO> getSequenceStepConditionParameter(@PathVariable Long id) {
        log.debug("REST request to get SequenceStepConditionParameter : {}", id);
        Optional<SequenceStepConditionParameterDTO> sequenceStepConditionParameterDTO = sequenceStepConditionParameterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequenceStepConditionParameterDTO);
    }

    /**
     * {@code DELETE  /sequence-step-condition-parameters/:id} : delete the "id" sequenceStepConditionParameter.
     *
     * @param id the id of the sequenceStepConditionParameterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @DeleteMapping("/sequence-step-condition-parameters/{id}")
    public ResponseEntity<Void> deleteSequenceStepConditionParameter(@PathVariable Long id) {
        log.debug("REST request to delete SequenceStepConditionParameter : {}", id);
        sequenceStepConditionParameterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
