package gcc.core

public enum Status {
  'NORMAL',
  'SELECTION',
  'ELITE'
}

public class Partner {

  private String _name;
  private String _address;
  private String _id;
  private Status _status;
  private double _points;
  private List<Batch> _batches;
  private List<Notification> _notifications;

  public Partner(String name, String address) {
    this._name = name;
    this._address = address;
    this._status = 'NORMAL';
    this._batches = new ArrayList<Batch>();
  }

  public String getName() {
    return this._name;
  }

  public String getAddress() {
    return this._address;
  }

  public String getId() {
    return this._id;
  }

  public Status getStatus() {
    return this._status;
  }

  public void setStatus(Status status) {
    this._status = status;
  }

  public double getPoints() {
    return this._points;
  }

  public void addPoints(double points) {
    this._points += points;
  }

  public void addBatch(Batch batch) {
    this._batches.add(batch);
  }

  public void removeBatch(Batch batch) {
    this._batches.remove(batch);
  }

  public void addNotification(Notification notification) {
    this._notifications.add(notification);
  }

  public void removeNotification(Notification notification) {
    this._notifications.remove(notification);
  }

}
