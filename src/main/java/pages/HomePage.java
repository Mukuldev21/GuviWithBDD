package pages;

import com.google.gson.JsonObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


import java.util.List;

public class HomePage extends BasePage {

    // Locator for GUVI logo on homepage
    @FindBy(css = "a[href='/'] .guvi_logo")
    private WebElement guviLogo;

    // Locator for "Paid Courses" tab
    @FindBy(css = "div.courses-tab[ q\\:key='paidcourse']")
    private WebElement paidCoursesTab;

    // Locator for the search input field
    @FindBy(css = "input[placeholder='Search for anything...']")
    private WebElement searchInput;

    // Locator for the search button
    @FindBy(css = "form.input-container svg.lucide-search")
    private WebElement searchButton;

    // Locators for Payment screen
    @FindBy(css = "div.fixed-div")
    private WebElement paymentFrame;

    @FindBy(css = "h3.mb-1")
    private WebElement paymentHeader;

    @FindBy(id = "payNowBtn")
    private WebElement payNowButton;

    // Locator for merchant logo image in payment frame
    @FindBy(css = "img[data-testid='merchnatLogoImage']")
    private WebElement merchantLogoImage;

    // Locator for merchant name in payment frame
    @FindBy(css = "div[data-testid='merchantName'] .font-heading")
    private WebElement merchantName;

    // Locator for "Payment Options" title in payment frame
    @FindBy(css = "span[data-testid='title']")
    private WebElement paymentOptionsTitle;

    // Locator for the close checkout button
    @FindBy(css = "button[data-testid='checkout-close']")
    private WebElement closeCheckoutButton;

    // Locator for the exit confirmation frame
    @FindBy(css = "div[data-testid='dialog-confirm-close']")
    private WebElement exitConfirmFrame;

    // Locator for the "Yes, exit" button
    @FindBy(css = "button[data-testid='confirm-positive']")
    private WebElement yesExitButton;

    // Locator for the payment exit modal body
    @FindBy(css = "div.modal-body img[src*='paymentsurvey1']")
    private WebElement paymentExitModalImage;

    @FindBy(css = "div.modal-body h6")
    private WebElement paymentExitModalTitle;

    @FindBy(css = "div.modal-body h2")
    private WebElement paymentExitModalSubtitle;

    // Locator for the "Exit payment" button
    @FindBy(id = "paymentcancelnbutton")
    private WebElement exitPaymentButton;

    // Locator for the modal title in the header
    @FindBy(css = "div.modal-header h5.modal-title")
    private WebElement helpUsModalTitle;

    // Locators for feedback checkboxes (question 1)
    @FindBy(id = "technicalissues")
    private WebElement technicalIssuesCheckbox;

    @FindBy(id = "notmuchcontent")
    private WebElement notMuchContentCheckbox;

    @FindBy(id = "lackofpaymentoptions")
    private WebElement lackOfPaymentOptionsCheckbox;

    @FindBy(id = "notbetterpricing")
    private WebElement notBetterPricingCheckbox;

    @FindBy(id = "bettercourseinotherplatform")
    private WebElement betterCourseInOtherPlatformCheckbox;

    @FindBy(id = "otherreason")
    private WebElement otherReasonCheckbox;

    @FindBy(id = "otherreasons")
    private WebElement otherReasonInput;

    // Question 2 checkboxes and input
    @FindBy(id = "accesstoaddtionalcourses")
    private WebElement accessToAdditionalCoursesCheckbox;

    @FindBy(id = "discounts")
    private WebElement discountsCheckbox;

    @FindBy(id = "otherreason1")
    private WebElement otherReason1Checkbox;

    @FindBy(id = "otherreasons1")
    private WebElement otherReason1Input;

    // Locator for the feedback submit button
    @FindBy(id = "feedbacksubmit")
    private WebElement feedbackSubmitButton;

    // Locator for feedback completion message
    @FindBy(xpath = "//div[contains(@class,'modal-body')]//h6[text()='Feedback submitted successfully!']")
    private WebElement feedbackCompletionMessage;

    // Locator for the close button in feedback completion modal
    @FindBy(id = "closefeedbackcompletion")
    private WebElement closeFeedbackCompletionButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Method to verify user is on homepage (logged in)
    public boolean isUserLoggedIn() {
        try {
            wait.until(ExpectedConditions.visibilityOf(guviLogo));
            return guviLogo.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Method to click on the "Paid Courses" tab
    /*public void clickPaidCoursesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(paidCoursesTab));
        paidCoursesTab.click();
    }*/

    public void clickPaidCoursesTab() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(paidCoursesTab));
            paidCoursesTab.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.paid-courses-content")));
        } catch (Exception e) {
            throw new RuntimeException("Error clicking Paid Courses tab: " + e.getMessage());
        }
    }

    // Optionally, a method to check if "Paid Courses" tab is visible
    public boolean isPaidCoursesTabVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(paidCoursesTab));
            return paidCoursesTab.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Method to enter text in the search input
    public void enterSearchText(String text) {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchInput));
            searchInput.clear();
            searchInput.sendKeys(text);
        } catch (Exception e) {
            throw new RuntimeException("Error entering search text: " + e.getMessage());
        }
    }

    // Method to check if the search input is visible
    public boolean isSearchInputVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchInput));
            return searchInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Method to click the search button
    public void clickSearchButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(searchButton));
            searchButton.click();
        } catch (Exception e) {
            throw new RuntimeException("Error clicking search button: " + e.getMessage());
        }
    }

    // Method to get all course result cards
    public List<WebElement> getCourseResults() {
        try {
            By courseResultCardsBy = By.cssSelector("a.progressCard");
            By noResultsMessageBy = By.xpath("//*[contains(text(),'No results found')]");
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(courseResultCardsBy),
                    ExpectedConditions.visibilityOfElementLocated(noResultsMessageBy)
            ));
            return driver.findElements(courseResultCardsBy);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching course results: " + e.getMessage());
        }
    }

    // Method to find a course by title in the results
    public WebElement findCourseByTitle(String title) {
        try {
            List<WebElement> results = getCourseResults();
            for (WebElement card : results) {
                WebElement titleElement = card.findElement(By.cssSelector("h3.progress-title"));
                if (titleElement.getText().trim().equalsIgnoreCase(title.trim())) {
                    return card;
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error finding course by title: " + e.getMessage());
        }
    }
    // Locator for "no results" message
    private final By noResultsMessageBy = By.cssSelector("span.no-course p");

    // Method to check if "no results" message is displayed
    public boolean isNoResultsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(noResultsMessageBy));
            WebElement message = driver.findElement(noResultsMessageBy);
            String expectedText = "No courses in our library match the keyword you entered.";
            return message.getText().contains(expectedText);
        } catch (Exception e) {
            return false;
        }
    }
    // Method to click the course if present, else check for "no results"
    /*public boolean clickCourseIfPresent(String title) {
        try {
            waitForLoaderToDisappear();
            WebElement courseCard = findCourseByTitle(title);
            if (courseCard != null) {
                wait.until(ExpectedConditions.elementToBeClickable(courseCard));
                utils.JavaScriptUtil.scrollIntoView(driver, courseCard);
                courseCard.click();
                waitForLoaderToDisappear();
                // Wait for a unique element on the course details page
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("enroll-now")));
                return true;
            } else {
                return isNoResultsDisplayed();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error clicking course: " + e.getMessage());
        }
    }*/
    // HomePage.java

    public boolean clickCourseIfPresent(String title) {
        try {
            waitForLoaderToDisappear();
            By courseCardBy = By.cssSelector("a.progressCard");
            List<WebElement> cards = driver.findElements(courseCardBy);
            for (WebElement card : cards) {
                WebElement titleElement = card.findElement(By.cssSelector("h3.progress-title"));
                if (titleElement.getText().trim().equalsIgnoreCase(title.trim())) {
                    wait.until(ExpectedConditions.elementToBeClickable(card));
                    utils.JavaScriptUtil.scrollIntoView(driver, card);
                    card.click();
                    waitForLoaderToDisappear();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("enroll-now")));
                    return true;
                }
            }
            return isNoResultsDisplayed();
        } catch (Exception e) {
            throw new RuntimeException("Error clicking course: " + e.getMessage());
        }
    }

    // Method to click the "Enroll Now" button on the course page
    public void clickEnrollNowButton() {
        By enrollNowButtonBy = By.id("enroll-now");
        wait.until(ExpectedConditions.elementToBeClickable(enrollNowButtonBy));
        driver.findElement(enrollNowButtonBy).click();
    }
    // Method to validate Payment screen is displayed
    public boolean isPaymentScreenDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(paymentFrame));
            return paymentFrame.isDisplayed() && paymentHeader.getText().equalsIgnoreCase("Payment");
        } catch (Exception e) {
            return false;
        }
    }

    // Method to click "Pay Now" button
    public void clickPayNow() {
        wait.until(ExpectedConditions.elementToBeClickable(payNowButton));
        payNowButton.click();
    }
    // Method to verify payment frame is visible
    public boolean isPaymentFrameVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(merchantLogoImage));
            wait.until(ExpectedConditions.visibilityOf(merchantName));
            wait.until(ExpectedConditions.visibilityOf(paymentOptionsTitle));
            return merchantLogoImage.isDisplayed()
                    && merchantName.isDisplayed()
                    && paymentOptionsTitle.isDisplayed()
                    && paymentOptionsTitle.getText().equalsIgnoreCase("Payment Options");
        } catch (Exception e) {
            return false;
        }
    }
    // Method to click the close checkout button
    public void clickCloseCheckoutButton() {
        wait.until(ExpectedConditions.elementToBeClickable(closeCheckoutButton));
        closeCheckoutButton.click();
    }
    // Method to validate user is on the exit confirmation frame and click "Yes, exit"
    public boolean confirmAndExit() {
        try {
            wait.until(ExpectedConditions.visibilityOf(exitConfirmFrame));
            if (exitConfirmFrame.isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(yesExitButton));
                yesExitButton.click();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // Method to validate the frame and click "Exit payment"
    public boolean validateAndExitPayment() {
        try {
            wait.until(ExpectedConditions.visibilityOf(paymentExitModalImage));
            wait.until(ExpectedConditions.visibilityOf(paymentExitModalTitle));
            wait.until(ExpectedConditions.visibilityOf(paymentExitModalSubtitle));
            if (paymentExitModalTitle.getText().contains("You’re almost there!")
                    && paymentExitModalSubtitle.getText().contains("Complete your purchase")) {
                wait.until(ExpectedConditions.elementToBeClickable(exitPaymentButton));
                exitPaymentButton.click();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // Method to verify user is on the help modal frame
    public boolean isHelpUsModalDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(helpUsModalTitle));
            return helpUsModalTitle.isDisplayed() &&
                    helpUsModalTitle.getText().trim().equals("Help us to understand you to help you better!");
        } catch (Exception e) {
            return false;
        }
    }
    // Add to HomePage.java

    public boolean verifyAndSelectFeedbackCheckbox(String feedbackReason, JsonObject json) {
        try {
            Map<String, WebElement> checkboxMap = new HashMap<>();
            checkboxMap.put("technicalissues", technicalIssuesCheckbox);
            checkboxMap.put("notmuchcontent", notMuchContentCheckbox);
            checkboxMap.put("lackofpaymentoptions", lackOfPaymentOptionsCheckbox);
            checkboxMap.put("notbetterpricing", notBetterPricingCheckbox);
            checkboxMap.put("bettercourseinotherplatform", betterCourseInOtherPlatformCheckbox);
            checkboxMap.put("otherreason", otherReasonCheckbox);

            if (checkboxMap.containsKey(feedbackReason)) {
                WebElement checkbox = checkboxMap.get(feedbackReason);
                if (!checkbox.isSelected()) {
                    checkbox.click();
                }
                if ("otherreason".equals(feedbackReason) && json.has("otherReasonText")) {
                    String otherText = json.get("otherReasonText").getAsString();
                    otherReasonInput.clear();
                    otherReasonInput.sendKeys(otherText);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyAndSelectImprovementCheckbox(String feedbackImprovement, JsonObject json) {
        try {
            Map<String, WebElement> checkboxMap = new HashMap<>();
            checkboxMap.put("accesstoaddtionalcourses", accessToAdditionalCoursesCheckbox);
            checkboxMap.put("discounts", discountsCheckbox);
            checkboxMap.put("otherreason1", otherReason1Checkbox);

            if (checkboxMap.containsKey(feedbackImprovement)) {
                WebElement checkbox = checkboxMap.get(feedbackImprovement);
                if (!checkbox.isSelected()) {
                    checkbox.click();
                }
                if ("otherreason1".equals(feedbackImprovement) && json.has("otherImprovementText")) {
                    String otherText = json.get("otherImprovementText").getAsString();
                    otherReason1Input.clear();
                    otherReason1Input.sendKeys(otherText);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // Method to click the feedback submit button
    public void clickFeedbackSubmitButton() {
        wait.until(ExpectedConditions.elementToBeClickable(feedbackSubmitButton));
        feedbackSubmitButton.click();
    }

    // Method to validate feedback completion frame and click close
    public boolean validateAndCloseFeedbackCompletion() {
        try {
            wait.until(ExpectedConditions.visibilityOf(feedbackCompletionMessage));
            wait.until(ExpectedConditions.elementToBeClickable(closeFeedbackCompletionButton));
            if (feedbackCompletionMessage.isDisplayed()) {
                closeFeedbackCompletionButton.click();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}