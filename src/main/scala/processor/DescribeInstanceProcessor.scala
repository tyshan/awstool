package processor

import scala.collection.JavaConversions._
import com.amazonaws.services.ec2.model.DescribeInstancesRequest
import com.amazonaws.services.ec2.model.DescribeInstancesResult
import com.amazonaws.services.ec2.model.Reservation
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult
import com.amazonaws.services.ec2.model.AvailabilityZone
import com.amazonaws.services.ec2.model.Instance
import java.util.HashSet

class DescribeInstanceProcessor extends BaseProcessor {

  def start(args: Array[String]) = {
    var describesRequest: DescribeInstancesRequest = new DescribeInstancesRequest()
    var describesResponse: DescribeInstancesResult = ec2.describeInstances()
    var availabilityZonesResult: DescribeAvailabilityZonesResult = ec2.describeAvailabilityZones();
    println("You have access to " + availabilityZonesResult.getAvailabilityZones().size() +
      " Availability Zones.");
    availabilityZonesResult.getAvailabilityZones.foreach { zone => println(zone.getZoneName) }

    var describeInstancesResult: DescribeInstancesResult = ec2.describeInstances();
    var reservations = describeInstancesResult.getReservations
    var instances: HashSet[Instance] = new HashSet[Instance]()
    reservations.foreach { reservation => instances.addAll(reservation.getInstances) }

    println("You have instances: " + instances.size)

    instances.foreach { instance => println(instance.getInstanceId + "\t" + instance.getState.getName) }

  }

}