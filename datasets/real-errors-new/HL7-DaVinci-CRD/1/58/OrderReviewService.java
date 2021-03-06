package org.hl7.davinci.endpoint.cdshooks.services.crd;

import org.hl7.davinci.Utilities;
import org.hl7.davinci.cdshooks.orderreview.OrderReviewFetcher;
import org.hl7.davinci.endpoint.components.CardBuilder;

import javax.validation.Valid;

import org.hl7.davinci.endpoint.database.CoverageRequirementRule;
import org.hl7.davinci.cdshooks.CdsResponse;
import org.hl7.davinci.cdshooks.CdsService;
import org.hl7.davinci.cdshooks.Hook;
import org.hl7.davinci.cdshooks.Prefetch;

import org.hl7.davinci.cdshooks.orderreview.OrderReviewRequest;

import org.hl7.davinci.endpoint.database.CoverageRequirementRuleFinder;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;


@Component
public class OrderReviewService extends CdsService {

  static final Logger logger = LoggerFactory.getLogger(OrderReviewService.class);

  public static final String ID = "order-review-crd";
  public static final String TITLE = "order-review Coverage Requirements Discovery";
  public static final Hook HOOK = Hook.ORDER_REVIEW;
  public static final String DESCRIPTION =
      "Get information regarding the coverage requirements for durable medical equipment";
  public static final Prefetch PREFETCH = null;

  @Autowired
  CoverageRequirementRuleFinder ruleFinder;

  public OrderReviewService() {
    super(ID, HOOK, TITLE, DESCRIPTION, PREFETCH);
  }

  /**
   * Handle the post request to the service.
   * @param request The json request, parsed.
   * @return
   */
  public CdsResponse handleRequest(@Valid @RequestBody OrderReviewRequest request) {
    logger.info("handleRequest: start");
    logger.info("Order bundle size: " + request.getContext().getOrders().getEntry().size());

    OrderReviewFetcher fetcher = new OrderReviewFetcher(request);
    //fetcher.fetch();

    // output some of the data
    if (request.getPrefetch().getPatient() != null) {
      logger.info("handleRequest: patient birthdate: "
          + request.getPrefetch().getPatient().getBirthDate().toString());
    }
    if (request.getPrefetch().getCoverage() != null) {
      logger.info("handleRequest: coverage id: "
          + request.getPrefetch().getCoverage().getId());
    }
    if (request.getPrefetch().getLocation() != null) {
      logger.info("handleRequest: location address: "
          + request.getPrefetch().getLocation().getAddress().getCity() + ", "
          + request.getPrefetch().getLocation().getAddress().getState());
    }
    if (request.getPrefetch().getInsurer() != null) {
      logger.info("handleRequest: insurer id: "
          + request.getPrefetch().getInsurer().getName());
    }
    if (request.getPrefetch().getProvider() != null) {
      logger.info("handleRequest: provider name: "
          + request.getPrefetch().getProvider().getName().get(0).getPrefixAsSingleString() + " "
          + request.getPrefetch().getProvider().getName().get(0).getFamily());
    }

    if (!fetcher.hasRequest()) {
      // TODO: raise error
      logger.error("No request provided!");
    }

    CdsResponse response = new CdsResponse();

    Patient patient = request.getPrefetch().getPatient();
    CodeableConcept cc = null;
    try {
      cc = request.getContext().firstOrderCode();
    } catch (FHIRException fe) {
      response.addCard(CardBuilder.summaryCard("Unable to parse the device code out of the request"));
    }
    if (patient == null) {
      response.addCard(CardBuilder.summaryCard("No patient could be (pre)fetched in this request"));
    }

    if (patient != null && cc != null) {
      int patientAge = Utilities.calculateAge(patient);
      CoverageRequirementRule crr = ruleFinder.findRule(patientAge, patient.getGender(), cc.getCoding().get(0).getCode());
      if (crr != null) {
        response.addCard(CardBuilder.transform(crr));
      } else {
        response.addCard(CardBuilder.summaryCard("No documentation rules found"));
      }
    }
    logger.info("handleRequest: end");
    return response;
  }

}
