package com.store.pawan.pawanstore.Utility;

/**
 * Created by shwetakumar on 7/27/17.
 */

public class Constants {

    //Url
    public static final String BASE_URL="https://cargocentral.herokuapp.com/";
    public static final String VEHICLE_LIST="vehicles/byStatus";
    public static final String ROUTE_LIST="routes";
    public static final String BRANCH_LIST="branch/list/json";

    //sharedPreferences
    public static final String PREF_NAME="jetty_pref";
    public static final String APP_MODE="app_mode";
    public static final String BRANCH_NAME="branch_name";
    public static final String BRANCH_ID="branch_id";



    // intent and arguments
    public  static final String SHOW_ISSUE_ON_TRUCK_DETAILS="show_issues";
    public  static final String SHIPMENT_ID="shipment_id";
    public static final String ISSUE_TYPE="issue_type";
    public static final String CURRENT_TRUCK="issue_type";

    public enum IssueType {
        DAMAGE("DAMAGE"),
        MISSING("MISSING"),
        WEIGHT("WEIGHT CHANGE");

        String issue_type;

        IssueType(String issue_type){
            this.issue_type=issue_type;
        }

        public String getIssue_type() {
            return issue_type;
        }
    }

    public enum AccountMode{
        LEND(0),
        PAY(1);

        int account_mode;

        AccountMode(int mode) {
            this.account_mode=mode;
        }

        public int getAccountMode(){
           return account_mode;
        }

    }

    public enum PaymentMode{
        PAID(0),
        REMAINING(1);

        int payment_mode;

        PaymentMode(int mode) {
            this.payment_mode=mode;
        }

        public int getPayment_mode(){
            return payment_mode;
        }

    }

    public static int[] GSTSlab={5,12,18,28};


}
