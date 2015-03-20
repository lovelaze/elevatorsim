import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bengan on 3/17/2015.
 */
public interface GroupControl {

	public abstract Car assignElevator(List<Shaft> shafts);

	public abstract void sendCalls(ArrayList<Call> calls);

}
