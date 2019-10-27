package domain;


public class Van {

  // ============================== ATTRIBUTES ============================== //

  private Integer stationID;          // origin station identifier
  static final Integer CAPACITY = 30;  // maximum number of bikes

  // =============================== METHODS ================================ //

  // ----------------------------- Constructors ----------------------------- //

  Van(final Integer estationID) {
    this.stationID = estationID;
  }

  // ------------------------------ Modifiers ------------------------------- //

  void setOriginStationID(final Integer stationID) {
    this.stationID = stationID;
  }

  // ----------------------------- Consultants ------------------------------ //

  Integer getOriginStationID() {
    return this.stationID;
  }

}
