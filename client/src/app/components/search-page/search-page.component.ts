import { Component, OnInit } from '@angular/core';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SearchService } from 'src/app/servies/search.service';

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.scss']
})
export class SearchPageComponent implements OnInit {
  faSearchIcon = faMagnifyingGlass;
  form: FormGroup;

  constructor(private fb: FormBuilder, private service: SearchService) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      search: ''
    });
  }

  get searchWord(): String {
    return this.form.get('search')?.value;
  }

  onClick() {
    this.service.getResult();
  }
}
