import java.time.LocalDate;

public class CustomerMembership {
    private int id;
    private String customerName;
    private Membership membership;  // Reference to membership type
    private LocalDate startDate;
    private LocalDate expirationDate;
    private String contactInfo;

    // Constructor
    public CustomerMembership(String customerName, Membership membership,
                              LocalDate startDate, LocalDate expirationDate, String contactInfo) {
        this.customerName = customerName;
        this.membership = membership;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.contactInfo = contactInfo;
    }

    // Constructor without contact info
    public CustomerMembership(String customerName, Membership membership,
                              LocalDate startDate, LocalDate expirationDate) {
        this(customerName, membership, startDate, expirationDate, "");
    }

    // Getters and setters
    public int getId() { return id; }
    protected void setId(int id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public Membership getMembership() { return membership; }
    public void setMembership(Membership membership) { this.membership = membership; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    @Override
    public String toString() {
        return "Customer: " + customerName +
                " | Membership: " + membership.getName() +
                " | Start: " + startDate +
                " | Expiration: " + expirationDate +
                (contactInfo != null && !contactInfo.isEmpty() ? " | Contact: " + contactInfo : "");
    }
}
