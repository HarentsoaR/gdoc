package mg.md2i.gedi.services;

import mg.md2i.gedi.entity.Log;
import java.util.List;

public interface MyService {

	Log addLog(Log log);

	List<Log> getLogs();

	void deleteLog(Log log);
}
