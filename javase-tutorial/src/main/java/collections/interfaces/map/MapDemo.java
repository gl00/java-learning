package collections.interfaces.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class MapDemo {
	public static void main(String[] args) {
		testAggregateOperations();
	}

	private static void testAggregateOperations() {
		List<Employee> employees = fakeData();

		System.out.println(employees);
		System.out.println();

		// Group employees by department
		System.out.println("按部门对员工进行分组");
		Map<Department, List<Employee>> byDept = employees.stream()
				.collect(Collectors.groupingBy(Employee::getDepartment));
		
		Set<Entry<Department, List<Employee>>> entrySet = byDept.entrySet();
		for (Entry<Department, List<Employee>> entry : entrySet) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		System.out.println();

		// Compute sum of salaries by department
		System.out.println("按部门计算所有工资的总和");
		Map<Department, Integer> totalByDept = employees.stream()
				.collect(Collectors.groupingBy(Employee::getDepartment,
						Collectors.summingInt(Employee::getSalary)));
		
		Set<Entry<Department, Integer>> entrySet2 = totalByDept.entrySet();
		for (Entry<Department, Integer> entry : entrySet2) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		System.out.println();
	}

	private static List<Employee> fakeData() {
		// 刘一 陈二 张三 李四 王五 赵六 孙七 周八 吴九 郑十
		// 财务部、销售部、采购部、综合人事部、生产部、开发部

		String[] names = {
				"刘一", "陈二", "张三", "李四", "王五",
				"赵六", "孙七", "周八", "吴九", "郑十" };

		Department[] ds = {
				new Department(1, "财务部"),
				new Department(2, "销售部"),
				new Department(3, "采购部"),
				new Department(4, "人事部"),
				new Department(5, "生产部"),
				new Department(6, "开发部") };

		Random r = new Random();
		List<Employee> employees = new ArrayList<>();
		for (int i = 0; i < names.length; i++) {
			Employee e = new Employee(i + 1,
					names[i],
					ds[r.nextInt(ds.length)],
					r.nextInt(10000) + 5000);
			employees.add(e);
		}
		return employees;
	}
}
