package mysite.expense.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mysite.expense.dto.ExpenseDTO;
import mysite.expense.dto.ExpenseFilterDTO;
import mysite.expense.entity.Expense;
import mysite.expense.service.ExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;


    @GetMapping("/expenses")
    public String showExpenseList(Model model) {
        List<ExpenseDTO> list = expenseService.getAllExpenses();
        model.addAttribute("expenses", list);
        model.addAttribute("filter", new ExpenseFilterDTO());
        Long total = expenseService.totalExpenses(list);
        model.addAttribute("total", total);
        return "expenses-list";
    }

    //get 요청시 비용 입력을 위한 창을 보여주기
    @GetMapping("createExpense")
    public String showCreateForm(Model model) {
        model.addAttribute("expense", new ExpenseDTO());    // 빈 expense전달
        return "expenses-form";
    }

    @PostMapping("/saveOrUpdateExpense")    // @ModelAttribute("expense") = expense 객체로 받겠다.
    public String saveOrUpdateExpense(@Valid @ModelAttribute("expense") ExpenseDTO expenseDTO, BindingResult result) throws ParseException {
        System.out.println(expenseDTO);

        if(result.hasErrors()) {
            return "expenses-form"; //에러 발생시 되돌아간다. (데이터 유효성 검사)
        }
        expenseService.saveExpenseDetails(expenseDTO);

        return "redirect:/expenses";
    }

    @GetMapping("/deleteExpense")
    public String deleteExpense(@RequestParam("id") String expenseId) {

        System.out.println("삭제번호 = " +expenseId);
        expenseService.deleteExpense(expenseId);
        return "redirect:/expenses";
    }

    // 수정 페이지 보여주기
    @GetMapping("/updateExpense")
    public String updateExpense(@RequestParam("id") String expenseId, Model model) {
        // db에서 해당 id의 expense 객체를 가져온다.
        model.addAttribute("expense", expenseService.getExpenseById(expenseId));
        System.out.println("변경할 사항 = " + expenseId);
        return "expenses-form";
    }


}
