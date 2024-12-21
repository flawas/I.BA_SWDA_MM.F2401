package ch.hslu.swda.api.controllers;

import ch.hslu.swda.api.services.PaymentService;
import ch.hslu.swda.data.dto.payment.PaymentDto;
import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Controller("/payments")
@ExecuteOn(TaskExecutors.IO)
@Slf4j
public final class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Get all payments (READ).
     * @return List of payments (PaymentDto).
     */
    @Get
    @Version("1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned all payments.")})
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Payments")
    public HttpResponse<List<PaymentDto>> getPayments() {
        final List<PaymentDto> payments = paymentService.getAll();
        log.info("API: Returning all {} payments.", payments.size());
        return HttpResponse.ok(payments);
    }

    /**
     * Get payments by customer email (READ).
     * @return List of payments (PaymentDto).
     */
    @Get("?customer={customerEmail}")
    @Version("1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned all payments of customer.")})
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Payments")
    public HttpResponse<List<PaymentDto>> getPaymentsByCustomerEmail(@QueryValue("customerEmail") final String customerEmail) {
        final List<PaymentDto> payments = paymentService.findByCustomerEmail(customerEmail);
        log.info("API: Returning all {} payments of customer '{}'.", payments.size(), customerEmail);
        return HttpResponse.ok(payments);
    }

    /**
     * Get payment by unique payment number (READ).
     * @param number Unique payment number.
     * @return Payment (PaymentDto).
     */
    @Get("/{number}")
    @Version("1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned requested payment data."),
            @ApiResponse(responseCode = "404", description = "Requested payment with provided email address was not found!")})
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Payments")
    public HttpResponse<?> getPayment(@PathVariable("number") final String number) {
        final Optional<PaymentDto> payment = paymentService.findByNumber(number);

        if (payment.isEmpty()) {
            log.warn("API: No payment found for given payment number '{}'!", number);
            return HttpResponse.notFound(String.format("No payment found for given payment number '%s'!", number));
        }

        log.info("API: Returning payment for number '{}'.", payment.get().number());
        return HttpResponse.ok(payment.get());
    }
}
