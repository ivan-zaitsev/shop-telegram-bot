package ua.ivan909020.bot.services;

public interface OrderStepService {

    void revokeOrderStep(Long chatId);

    void previousOrderStep(Long chatId);

    void nextOrderStep(Long chatId);

}
