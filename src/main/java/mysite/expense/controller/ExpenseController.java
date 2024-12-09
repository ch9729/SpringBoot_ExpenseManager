package mysite.expense.controller;

import lombok.RequiredArgsConstructor;
import mysite.expense.dto.ExpenseDTO;
import mysite.expense.entity.Expense;
import mysite.expense.service.ExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

@Controller
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;


    @GetMapping("/expenses")
    public String showExpenseList(Model model) {
        model.addAttribute("expenses", expenseService.getAllExpenses());
        return "expenses-list";
    }

    //get 요청시 비용 입력을 위한 창을 보여주기
    @GetMapping("createExpense")
    public String showCreateForm(Model model) {
        model.addAttribute("expense", new ExpenseDTO());
        return "expenses-form";
    }

    @PostMapping("/saveOrUpdateExpense")    // @ModelAttribute("expense") = expense 객체로 받겠다.
    public String saveOrUpdateExpense(@ModelAttribute("expense") ExpenseDTO expenseDTO) throws ParseException {
        System.out.println(expenseDTO);
        expenseService.saveExpenseDetails(expenseDTO);
        return "redirect:/expenses";
    }

    @GetMapping("/deleteExpense")
    public String deleteExpense(@RequestParam("id") String expenseId) {

        System.out.println("삭제번호 = " +expenseId);
        expenseService.deleteExpense(expenseId);
        return "redirect:/expenses";
    }

    @GetMapping("/updateExpense")
    public String updateExpense(@RequestParam("id") String expenseId) {
        System.out.println("변경할 사항 = " + expenseId);

        return "expenses-form";
    }
}
