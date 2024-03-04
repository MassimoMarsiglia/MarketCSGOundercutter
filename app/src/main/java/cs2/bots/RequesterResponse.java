package cs2.bots;

public class RequesterResponse {
    
        private String responseBody;
        private int statusCode;
    
        public RequesterResponse(String responseBody, int statusCode) {
            this.responseBody = responseBody;
            this.statusCode = statusCode;
        }
    
        public String getResponseBody() {
            return responseBody;
        }
    
        public int getStatusCode() {
            return statusCode;
        }
    }
